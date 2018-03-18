package me.xiaff.crawler.acmfellow.processor.google;

import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Dell-lcw on 2017/7/13.
 */
public class GoogleWikiSearchProcessor {
    private WebDriver webDriver;

    public GoogleWikiSearchProcessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public String getWikiUrl(String fellowId) {
//        String url;
//        try {
//            url = "https://www.google.com/search?q=" +
//                    URLEncoder.encode(fellowId + " site:en.wikipedia.org", "utf-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            System.exit(1);
//            return null;
//        }
        String html = HttpRequestFactory.getGoogleSearchPage(fellowId + " site:en.wikipedia.org");
        Document document = Jsoup.parse(html);
        Elements elements = document.select("#rso > div > div > div > div > div > h3 > a");
//        webDriver.get(url);
//        List<WebElement> elements = webDriver.findElements(By.cssSelector("#rso > div > div > div > div > div > h3 > a"));
        if (CollectionUtils.isEmpty(elements)) {
            return null;
        }

        String title = elements.get(0).text();
        if (!checkTitleContainsKeyword(title, fellowId)) {
            return null;
        }
        return elements.get(0).attr("href");
    }


    private boolean checkTitleContainsKeyword(String resultTitle, String fellowId) {
        resultTitle = resultTitle.toLowerCase();
        fellowId = fellowId.toLowerCase();
        String[] split = fellowId.split(",");
        for (String s : split) {
            if (!resultTitle.contains(s.trim())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws MalformedURLException {
        WebDriver webDriver = new RemoteWebDriver(new URL("http://127.0.0.1:10086"), DesiredCapabilities.chrome());
        GoogleWikiSearchProcessor processor = new GoogleWikiSearchProcessor(webDriver);
        System.out.println(processor.getWikiUrl("Ester, Martin"));
    }
}
