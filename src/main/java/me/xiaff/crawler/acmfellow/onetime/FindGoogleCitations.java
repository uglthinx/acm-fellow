package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.CiteNumber;
import me.xiaff.crawler.acmfellow.entity.Paper;
import me.xiaff.crawler.acmfellow.processor.GoogleCitationProcessor;
import me.xiaff.crawler.acmfellow.repo.PaperRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class FindGoogleCitations {
    private static final Logger logger = LoggerFactory.getLogger(FindGoogleCitations.class);
    private static final int THREAD_NUM = 6;

    @Autowired
    private PaperRepo paperRepo;


//    @Override
    public void run(String... args) throws Exception {
        List<Paper> papers = paperRepo.findAll();
        papers = papers.stream()
                .filter(paper -> (paper.getCitationNum2() == null &&
                        (paper.getCitation() == null || paper.getCitation() == 0)))
                .collect(Collectors.toList());
        logger.info("# papers = {}.", papers.size());

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (Paper paper : papers) {
            String title = paper.getTitle();
            String authors = paper.getAuthor();
            String key = title;
            if (authors != null && !authors.equals("null")) {
                key += " " + authors.split(" and ")[0];
            }
            final String finalKey = key;
            executorService.execute(() -> {
                logger.info("Finding citation of [{}]...", finalKey);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GoogleCitationProcessor processor = new GoogleCitationProcessor();
                CiteNumber citation = processor.getCitation(finalKey);
                paper.setCitationNum1(citation.getScholarCite());
                paper.setCitationNum2(citation.getSearchCite());
                paperRepo.save(paper);
                logger.info("{}-->[{}].", finalKey, citation);
            });
        }
    }
}
