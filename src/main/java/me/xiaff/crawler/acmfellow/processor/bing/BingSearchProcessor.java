package me.xiaff.crawler.acmfellow.processor.bing;

import me.xiaff.crawler.acmfellow.processor.SearchProcessor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class BingSearchProcessor extends SearchProcessor {

    public BingSearchProcessor(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public List<String> searchHomePageUrl(String name, String affiliation) {
        try {
            webDriver.get("https://www.bing.com/search?q=" + URLEncoder.encode(name + " " + affiliation, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 100);
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#b_results li")));
        List<WebElement> elements = webDriver.findElements(By.cssSelector("#b_results li h2 a"));

        return getUrlList(name, elements);
    }

}
