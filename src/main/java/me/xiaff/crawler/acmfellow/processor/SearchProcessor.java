package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.onetime.NormalPagePhdSearchRunner;
import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class SearchProcessor {

    private static List<String> CAN_NOT_CONTAIN_URL_LIST = new ArrayList<String>() {
        {
            add("google.com");
            add("linkedin.com");
            add("twitter.com");
            add("acm.org");
            add("acm.org");
            add("amazon.com");
            add("ieeexplore.ieee.org");
            add("ratemyprofessors.com");
            add("reimageplus.com");
            add("twitter.com");
            add("facebook.com");
            add("jacksonville.com");
            add("leadershipdirectories.com");
            add("researchgate.net");
            add("elsevier.com");
            add("projecteuclid.org");
            add("euclid.jmsj");
            add("kyoto-u.ac.jp");
            add("mj-smith.com");
            add("uni-trier.de");
            add("wiley.com");
            add("espn.com");
            add("whitepages.com");
            add("codercorner.com");
            add("aminer.org");
            add("academia.edu");
        }
    };
    protected WebDriver webDriver;

    public SearchProcessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public abstract List<String> searchHomePageUrl(String name, String affiliation);

    protected List<String> getUrlList(String name, List<WebElement> linkElements) {
        if (CollectionUtils.isEmpty(linkElements)) {
            return null;
        }
        List<String> urlList = new ArrayList<>();
        for (int i = 0; i < Math.min(10, linkElements.size()); i++) {
            String title = linkElements.get(i).getText();
            if (!checkTitleContainsKeyword(title, name)) {
                continue;
            }
            String url = linkElements.get(i).getAttribute("href");
            if (isSkipped(url)) {
                continue;
            }
            urlList.add(linkElements.get(i).getAttribute("href"));
        }
        return urlList;
    }

    private boolean checkTitleContainsKeyword(String resultTitle, String name) {
        resultTitle = resultTitle.toLowerCase();
        resultTitle = resultTitle.replace("-", " ");
        resultTitle = resultTitle.replace("'", " ");
        List<String> titleTokens = Arrays.asList(resultTitle.split(" "));
        System.out.println(resultTitle);
        name = name.toLowerCase();
        String[] split = name.split(", ");
        for (String token : split) {
            token = token.trim().replace(".", "");
            if (token.length() == 1) {
                continue;
            }
            int a = 1; // Fake statement
            if (!titleTokens.contains(token)) {
                return false;
            }
        }
        return true;
    }

    private boolean isSkipped(String url) {
        for (String blockSite : CAN_NOT_CONTAIN_URL_LIST) {
            if (url.contains(blockSite)) {
                return true;
            }
        }
        return NormalPagePhdSearchRunner.FAIL_URL_SET.contains(url);
    }

}
