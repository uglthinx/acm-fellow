package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.processor.bing.BingWikiSearchProcessor;
import me.xiaff.crawler.acmfellow.processor.WikiPageProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Search scholar's doctoral school from wikipeadia page
 * Created by Dell-lcw on 2017/7/13.
 */
@Component
public class SearchWikiSchoolRunner {
    public static final Logger logger = LoggerFactory.getLogger(SearchWikiSchoolRunner.class);
    @Autowired
    private AminerScholarRepo aminerScholarRepo;

    public void searchACMFellow(WebDriver bingWebDriver, WebDriver wikiWebDriver) throws UnsupportedEncodingException {
        List<AminerScholar> scholars = aminerScholarRepo.findAll();
        scholars = scholars.stream().filter(s -> s.getAcmFellow() == null
                && s.getIeeeFellow() == null
                && s.getPhdSchool() == null)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(scholars)) {
            return;
        }
//        GoogleWikiSearchProcessor googleWikiSearchProcessor = new GoogleWikiSearchProcessor(googleWebDriver);
        BingWikiSearchProcessor bingWikiSearchProcessor = new BingWikiSearchProcessor(bingWebDriver);
        WikiPageProcessor wikiPageProcessor = new WikiPageProcessor(wikiWebDriver);

        for (AminerScholar scholar : scholars) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ignored) {
            }

            String name = scholar.getName();
            String wikiUrl = bingWikiSearchProcessor.searchWikiUrl(name);
            if (StringUtils.isEmpty(wikiUrl)) {
                continue;
            }
            String phdSchool = wikiPageProcessor.getPhdSchool(wikiUrl);
            if (StringUtils.isEmpty(phdSchool)) {
                continue;
            }
            scholar.setPhdSchool(phdSchool);
            scholar.setPhdSchoolLink(wikiUrl);
            aminerScholarRepo.save(scholar);
            logger.info("[{}]: [{}]", name, phdSchool);
        }
    }
}
