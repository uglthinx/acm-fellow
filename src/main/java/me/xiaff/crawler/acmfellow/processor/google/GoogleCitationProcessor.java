package me.xiaff.crawler.acmfellow.processor.google;

import me.xiaff.crawler.acmfellow.entity.CiteNumber;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class GoogleCitationProcessor {


    public CiteNumber getCitation(String key) {

        String html = HttpRequestFactory.getGoogleSearchPage(key);
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        Document document = Jsoup.parse(html);
//        System.out.println(document.body());

        CiteNumber citeNumber = new CiteNumber();
        Elements scholarCite = document.select("#topstuff span.f");
        Elements searchCite = document.select("#rso .g:nth-child(1) div.slp a.fl:nth-child(1)");
        citeNumber.setScholarCite(getCiteNumFromElements(scholarCite));
        citeNumber.setSearchCite(getCiteNumFromElements(searchCite));

        return citeNumber;
    }

    private long getCiteNumFromElements(Elements elements) {
        if (CollectionUtils.isNotEmpty(elements)) {
            String citationText = elements.text();
            if (citationText.contains("被引用次数")) {
                String[] tokens = citationText.split("：");
                if (tokens.length >= 2) {
                    return (Long.parseLong(tokens[1].split(" ")[0]));
                }
            }
        }
        return 0L;
    }

    public static void main(String[] args) {
        GoogleCitationProcessor citationProcessor = new GoogleCitationProcessor();
        System.out.println(citationProcessor.getCitation("Subspace learning via Locally Constrained A-optimal nonnegative projection"));
        System.out.println(citationProcessor.getCitation("Text Alignment with Handwritten Documents"));
    }
}
