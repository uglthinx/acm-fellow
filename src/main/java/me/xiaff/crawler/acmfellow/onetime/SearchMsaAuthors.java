package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.FullFellowFeature;
import me.xiaff.crawler.acmfellow.entity.MsaAuthor;
import me.xiaff.crawler.acmfellow.processor.msa.MsaSearchProcessor;
import me.xiaff.crawler.acmfellow.repo.FullFellowFeatureRepo;
import me.xiaff.crawler.acmfellow.repo.MsaAuthorRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Component
public class SearchMsaAuthors {
    private static final int THREAD_NUM = 5;
    private static final Logger log = LoggerFactory.getLogger(SearchMsaAuthors.class);

    @Resource
    private FullFellowFeatureRepo fullFellowFeatureRepo;
    @Resource
    private MsaAuthorRepo msaAuthorRepo;

    private MsaSearchProcessor msaSearchProcessor = new MsaSearchProcessor();

    private ExecutorService executorService;

    //    @Override
    public void run(String... args) {
        System.out.println(Arrays.toString(args));
        System.out.println("============================================");
        System.out.println("==============SearchMsaAuthors==============");
        System.out.println("============================================");
        List<FullFellowFeature> fellows = fullFellowFeatureRepo.findUnsatisfiedFellows();
        log.info("# unsatisfied fellow: {}", fellows.size());

        List<FullFellowFeature> filteredFellows = fellows.stream()
                .filter(f -> msaAuthorRepo.findByName(f.getName()) == null)
                .collect(Collectors.toList());
        log.info("# need to search: {}.", filteredFellows.size());

        executorService = Executors.newFixedThreadPool(THREAD_NUM);
        for (FullFellowFeature fellow : filteredFellows) {
            String name = fellow.getName();
            if (msaAuthorRepo.findByName(name) != null) {
                continue;
            }
            addToQueue(name);
//            Thread.sleep(1500);
        }

//        executorService.shutdown();
    }

    private void addToQueue(String name) {
        executorService.execute(() -> {
            log.info("Finding [{}]...", name);
            MsaAuthor msaAuthor = msaSearchProcessor.searchScholarId(name);
            if (msaAuthor != null) {
                if (msaAuthor.getId() == 0L) {
                    return;
                }
                log.info(msaAuthor.toString());
                msaAuthorRepo.save(msaAuthor);
            }else {
                addToQueue(name);
            }
        });
    }
}
