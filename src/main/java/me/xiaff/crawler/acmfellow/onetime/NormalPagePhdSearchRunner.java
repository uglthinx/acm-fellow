package me.xiaff.crawler.acmfellow.onetime;

import com.google.common.collect.Lists;
import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.processor.NormalPageProcessor;
import me.xiaff.crawler.acmfellow.processor.SearchProcessor;
import me.xiaff.crawler.acmfellow.processor.google.GoogleSearchProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 从Google搜索人名，找到前五条，依次打开页面查找Ph.D学校
 * Created by Lu Chenwei on 2017/7/14.
 */
@Component
public class NormalPagePhdSearchRunner {
    public static final Logger logger = LoggerFactory.getLogger(NormalPagePhdSearchRunner.class);

    public static Set<String> FAIL_URL_SET = new HashSet<>();

    @Autowired
    private AminerScholarRepo aminerScholarRepo;

    public void run(WebDriver bingWebDriver, WebDriver normalDriver) throws Exception {
        List<AminerScholar> scholars = aminerScholarRepo.findAll();
        scholars = scholars.stream().filter(s -> s.getAcmFellow() == null
                && s.getIeeeFellow() == null
                && s.getPhdSchool() == null)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(scholars)) {
            return;
        }

//        bingWebDriver.get("https://www.bing.com/?ensearch=1");
//        BingSearchProcessor normalSearchProcessor = new BingSearchProcessor(bingWebDriver);
        bingWebDriver.get("https://www.google.com/ncr");
        SearchProcessor normalSearchProcessor = new GoogleSearchProcessor(bingWebDriver);
        NormalPageProcessor normalPageProcessor = new NormalPageProcessor(normalDriver);
        for (AminerScholar scholar : scholars) {
            long sleepTime = (long) (5000 * new Random().nextDouble()) + 1000L;
            logger.info("Sleeping for {}s...", (double) sleepTime / 1000);
            Thread.sleep(sleepTime);
            String name = scholar.getName();
            List<String> urlList = normalSearchProcessor.searchHomePageUrl(name, scholar.getAffiliation());
            if (CollectionUtils.isEmpty(urlList)) {
                continue;
            }
            for (String url : urlList) {
                Pair<String, String> phdSchoolAndYear = normalPageProcessor.getPhdSchoolAndYear(url);
                logger.info("{}: {} @[{}]", name, phdSchoolAndYear, url);
                if (phdSchoolAndYear == null) {
                    FAIL_URL_SET.add(url);
                    continue;
                }
                scholar.setPhdSchool(phdSchoolAndYear.getKey());
                scholar.setPhdSchoolLink(url);
                aminerScholarRepo.save(scholar);
                break;
            }
        }
    }

}
