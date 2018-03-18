package me.xiaff.crawler.acmfellow.processor.bing;

import org.apache.commons.collections.CollectionUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BingWikiSearchProcessor {
    private WebDriver webDriver;

    public BingWikiSearchProcessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String searchWikiUrl(String name) throws UnsupportedEncodingException {
        webDriver.get("https://www.bing.com/search?q=" + URLEncoder.encode(name + " site:en.wikipedia.org",
                "utf-8"));

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 100);
        webDriverWait.until(w -> w.findElements(By.cssSelector("#b_results li")).size() > 0);
        List<WebElement> elements = webDriver.findElements(By.cssSelector("#b_results li h2 a"));
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }
        String title = elements.get(0).getText();
        if (!checkTitleContainsKeyword(title, name)) {
            return null;
        }
        return elements.get(0).getAttribute("href");
    }

    private boolean checkTitleContainsKeyword(String resultTitle, String name) {
        resultTitle = resultTitle.toLowerCase();
        System.out.println(resultTitle);
        name = name.toLowerCase();
        String[] split = name.split(", ");
        for (String token : split) {
            token = token.trim().replace(".", "");
            if (token.length() == 1) {
                continue;
            }
            if (!resultTitle.contains(token)) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) throws Exception {
//        WebDriver webDriver = new RemoteWebDriver(new URL("http://127.0.0.1:10086"), DesiredCapabilities.chrome());
//        BingWikiSearchProcessor processor = new BingWikiSearchProcessor(webDriver);
//        System.out.println(processor.searchWikiUrl("Ester, Martin"));
    }
}
