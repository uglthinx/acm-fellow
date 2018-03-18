package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.util.HtmlUtils;
import me.xiaff.crawler.acmfellow.util.NerUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dell-lcw on 2017/7/13.
 */
public class WikiPageProcessor {
    private WebDriver webDriver;

    public WikiPageProcessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getPhdSchool(String url) {
        webDriver.get(url);
        List<WebElement> elements = webDriver.findElements(By.cssSelector("#mw-content-text > div > table.infobox.vcard"));
        if (CollectionUtils.isEmpty(elements)) {
            return getPhdSchoolFromText(webDriver.findElement(By.cssSelector("body")).getText());
        }
        WebElement table = elements.get(0);
        List<WebElement> lines = table.findElements(By.cssSelector("tr"));
        for (WebElement line : lines) {
            List<WebElement> ths = line.findElements(By.cssSelector("th"));
            if (CollectionUtils.isEmpty(ths)) {
                continue;
            }
            WebElement th = line.findElement(By.cssSelector("th"));
            String title = th.getText();
            if (!"Alma mater".equals(title)) {
                continue;
            }
            List<WebElement> links = line.findElements(By.cssSelector("td > a"));
            List<String> linkTitles = new ArrayList<>();
            for (WebElement link : links) {
                linkTitles.add(link.getAttribute("title"));
            }
            if (CollectionUtils.isEmpty(linkTitles)) {
                continue;
            }

            return linkTitles.get(0);
        }
        return getPhdSchoolFromText(webDriver.findElement(By.cssSelector("body")).getText());
    }

    private String getPhdSchoolFromText(String text) {
        if (StringUtils.isEmpty(text)) {
            return null;
        }

        Pair<String, String> phdSchoolAndYear = NerUtils.getPhdSchoolAndYear(HtmlUtils.getText(text));
        if (phdSchoolAndYear == null) {
            return null;
        }
        return phdSchoolAndYear.getKey();


    }
}
