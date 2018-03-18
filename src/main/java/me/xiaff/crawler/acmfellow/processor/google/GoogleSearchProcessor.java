package me.xiaff.crawler.acmfellow.processor.google;

import me.xiaff.crawler.acmfellow.processor.SearchProcessor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GoogleSearchProcessor extends SearchProcessor{

    public GoogleSearchProcessor(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public List<String> searchHomePageUrl(String name, String affiliation) {
        try {
            webDriver.get("https://www.google.com/search?q=" + URLEncoder.encode(name + " " + affiliation, "utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }

        WebDriverWait webDriverWait = new WebDriverWait(webDriver, 100);
        // #rso > div > div > div:nth-child(1) > div > div > h3 > a
        webDriverWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("#rso div.rc h3.r a")));
        List<WebElement> elements = webDriver.findElements(By.cssSelector("#rso div.rc h3.r a"));

        return super.getUrlList(name, elements);
    }
}
