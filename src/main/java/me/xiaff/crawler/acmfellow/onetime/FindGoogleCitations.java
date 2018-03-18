package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.CiteNumber;
import me.xiaff.crawler.acmfellow.entity.MsaPaper;
import me.xiaff.crawler.acmfellow.entity.Paper;
import me.xiaff.crawler.acmfellow.processor.google.GoogleCitationProcessor;
import me.xiaff.crawler.acmfellow.repo.MsaPaperRepo;
import me.xiaff.crawler.acmfellow.repo.PaperRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class FindGoogleCitations {
    private static final Logger logger = LoggerFactory.getLogger(FindGoogleCitations.class);
    private static final int THREAD_NUM = 1;

//    @Autowired
//    private PaperRepo paperRepo;

    @Autowired
    private MsaPaperRepo paperRepo;

    @Autowired
    private PaperRepo oldPaperRepo;


    public void run(String... args) throws Exception {
        List<MsaPaper> papers = paperRepo.findAll();
        papers = papers.stream()
                .filter(paper -> paper.getgCitation() == null)
                .collect(Collectors.toList());
        logger.info("# papers = {}.", papers.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (MsaPaper paper : papers) {
            String title = paper.getTitle();
            String authors = paper.getAuthors();
            String key = title;
            if (authors != null && !authors.equals("null")) {
                key += " " + authors.split(" and ")[0];
            }
            final String finalKey = key;
            executorService.execute(() -> {
                logger.info("Finding citation of [{}]...", finalKey);
                try {
                    Thread.sleep((long) (5000 * new Random().nextDouble()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GoogleCitationProcessor processor = new GoogleCitationProcessor();
                CiteNumber citation = processor.getCitation(finalKey);

                if (citation == null) {
                    System.exit(-1);
                }
                int gCitation1 = (int) citation.getScholarCite();
                int gCitation2 = (int) citation.getSearchCite();
                paper.setgCitation(Math.max(gCitation1, gCitation2));
                paperRepo.save(paper);
                logger.info("{}-->[{}].", finalKey, citation);
            });
        }
//        executorService.shutdown();
    }

    public void finExtraPaperCites() {
        List<Paper> papers = oldPaperRepo.findAll();
        papers = papers.stream().filter(p -> p.getCitation() == null).collect(Collectors.toList());
        logger.info("# papers: {}", papers.size());
        for (Paper paper : papers) {
            String title = paper.getTitle();
            String authors = paper.getAuthor();
            String key = title;
            if (authors != null && !authors.equals("null")) {
                key += " " + authors.split(" and ")[0];
            }
            try {
                Thread.sleep((long) (5000 * new Random().nextDouble()));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GoogleCitationProcessor processor = new GoogleCitationProcessor();
            CiteNumber citation = processor.getCitation(key);

            if (citation == null) {
                System.exit(-1);
            }
            int gCitation1 = (int) citation.getScholarCite();
            int gCitation2 = (int) citation.getSearchCite();
            paper.setCitation(Math.max(gCitation1, gCitation2));
            oldPaperRepo.save(paper);
            logger.info("{}-->[{}].", key, citation);
        }
    }
}
