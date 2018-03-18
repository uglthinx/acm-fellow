package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.FullFellowFeature;
import me.xiaff.crawler.acmfellow.entity.GScholar;
import me.xiaff.crawler.acmfellow.processor.google.GScholarSearchProcessor;
import me.xiaff.crawler.acmfellow.repo.FullFellowFeatureRepo;
import me.xiaff.crawler.acmfellow.repo.GScholarRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.DefaultApplicationArguments;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class FindGoogleScholars {
    private static final Logger LOGGER = LoggerFactory.getLogger(FindGoogleScholars.class);

    @Resource
    private FullFellowFeatureRepo fellowFeatureRepo;
    @Resource
    private GScholarRepo gScholarRepo;

    private GScholarSearchProcessor scholarSearchProcessor = new GScholarSearchProcessor();

    //    @Override
    public void run(String... args) throws Exception {
        DefaultApplicationArguments appArgs = new DefaultApplicationArguments(args);
        int threadNum = 1;
        System.out.println(appArgs.getOptionNames());
        if (appArgs.containsOption("thread")) {
            threadNum = Integer.parseInt(appArgs.getOptionValues("thread").get(0));
        }
        LOGGER.info("Thread num = {}.", threadNum);
        System.out.println("============================================");
        System.out.println("=============FindGoogleScholars=============");
        System.out.println("============================================");
        List<FullFellowFeature> fellows = fellowFeatureRepo.findUnsatisfiedFellows();
        LOGGER.info("# unsatisfied fellow: {}", fellows.size());

        ExecutorService executorService = Executors.newFixedThreadPool(threadNum);
        for (FullFellowFeature fellow : fellows) {
            executorService.submit(() -> {
                String name = fellow.getName();
                if (gScholarRepo.findByName(name) != null) {
                    return;
                }
                LOGGER.info("Finding [{}]...", name);
                GScholar gScholar = scholarSearchProcessor.searchScholar(name);
                if (gScholar != null) {
                    LOGGER.info(gScholar.toString());
                    gScholarRepo.save(gScholar);
                }

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException ignore) {
                }
            });
        }

        executorService.shutdown();
    }
}
