package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.MsaAuthor;
import me.xiaff.crawler.acmfellow.entity.MsaAuthorPaper;
import me.xiaff.crawler.acmfellow.entity.MsaPaper;
import me.xiaff.crawler.acmfellow.processor.msa.MsaApiProcessor;
import me.xiaff.crawler.acmfellow.repo.MsaAuthorPaperRepo;
import me.xiaff.crawler.acmfellow.repo.MsaAuthorRepo;
import me.xiaff.crawler.acmfellow.repo.MsaPaperRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class FindMsaAuthorPapers {
    private static final Logger log = LoggerFactory.getLogger(SearchMsaAuthors.class);

    @Resource
    private MsaAuthorRepo msaAuthorRepo;
    @Resource
    private MsaPaperRepo msaPaperRepo;
    @Resource
    private MsaAuthorPaperRepo msaAuthorPaperRepo;

    private MsaApiProcessor msaApiProcessor = new MsaApiProcessor();


    //    @Override
    public void run(String... args) throws Exception {
        System.out.println("============================================");
        System.out.println("==============FindMsaAuthorPapers==============");
        System.out.println("============================================");
        List<MsaAuthor> authors = msaAuthorRepo.findAll();
        authors = authors.stream()
                .filter(a -> a.getConsistent() == null || a.getConsistent())
                .filter(a -> a.getCrawled() == null || !a.getCrawled())
                .collect(Collectors.toList());
        log.info("# MSA authors: {}", authors.size());

        for (MsaAuthor author : authors) {
            Long id = author.getId();
            if (author.getCrawled() != null && author.getCrawled()) {
                continue;
            }
            log.info("Finding [{}]...", author.getName());

            List<MsaPaper> papers = msaApiProcessor.getAuthorPapers(id);
            log.info("# papers of [{}]: {}.", author.getName(), papers.size());
            addPaperAuthorRelations(id, author.getName(), papers);
            msaPaperRepo.save(papers);

            author.setCrawled(true);
            msaAuthorRepo.save(author);
            Thread.sleep((long) (3000 * new Random().nextDouble()));
        }
    }

    private void addPaperAuthorRelations(long authorId, String authorName, List<MsaPaper> papers) {
        List<MsaAuthorPaper> authorPaperList = new ArrayList<>();
        for (MsaPaper paper : papers) {
            if (msaAuthorPaperRepo.countByRelation(authorId, paper.getId()) > 0) {
                continue;
            }
            authorPaperList.add(new MsaAuthorPaper(authorId, authorName, paper.getId(), paper.getYear(), paper.getCitation()));
        }
        msaAuthorPaperRepo.save(authorPaperList);
    }
}
