package me.xiaff.crawler.acmfellow.processor.google;

import me.xiaff.crawler.acmfellow.entity.GScholar;
import me.xiaff.crawler.acmfellow.util.EnglishNameUtils;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.List;

public class GScholarSearchProcessor {

    private String getUserId(String url) {
        try {
            List<NameValuePair> nameValuePairs = URLEncodedUtils.parse(new URI(url), Charset.forName("utf-8"));
            for (NameValuePair pair : nameValuePairs) {
                if (pair.getName().equals("user")) {
                    return pair.getValue();
                }
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GScholar searchScholar(String name) {
        String page = HttpRequestFactory.getGoogleScholarSearchPage(name);
        if (StringUtils.isEmpty(page)) {
            return null;
        }
        Document document = Jsoup.parse(page);
        System.out.println(document.body());
        Elements scholars = document.select("h4.gs_rt2");
        for (Element scholar : scholars) {
            String fullName = scholar.text().trim();
            if (!EnglishNameUtils.isSameName(name, fullName)) {
                continue;
            }
            String url = "https://scholar.google.com.hk" + scholar.select("a").attr("href");
            String id = getUserId(url);
            return new GScholar(id, name, fullName, url);
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(new GScholarSearchProcessor().searchScholar("Li, Ming"));
    }
}
