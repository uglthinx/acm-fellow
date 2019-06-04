package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.entity.AcmFellow;
import me.xiaff.crawler.acmfellow.util.HttpClientDownloader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

public class FellowListPageProcessor implements PageProcessor {
    private static final String BASE_URL = "https://awards.acm.org";
    private static final String TYPE_1 = "acm";
    private static final String TYPE_2 = "fellow";
//    private static final String TYPE_2 = "distinguished";

    @Override
    public void process(Page page) {
        List<AcmFellow> fellowList = new ArrayList<>();
        String html = page.getHtml().toString();
        Document document = Jsoup.parse(html);
        Elements rows = document.select("#SkipTarget > div:nth-child(2) > div.columns.large-8.medium-8.small-12.zone-2 > div > div > div > div > div > table > tbody > tr");
//        Elements rows = document.select("#SkipTarget > div > div > div > div > div > div > div > table > tbody > tr");
        for (Element row : rows) {
            Elements tds = row.select("td");
            String name = tds.get(0).text();
            String url = BASE_URL + tds.get(0).select("a").attr("href");
            int year = Integer.parseInt(tds.get(2).text());
            String region = tds.get(3).text();
            AcmFellow fellow = new AcmFellow();
            fellow.setName(name);
            fellow.setRegion(region);
            fellow.setSelectYear(year);
            fellow.setUrl(url);
            fellow.setType1(TYPE_1);
            fellow.setType2(TYPE_2);
            System.out.println(fellow);
            fellowList.add(fellow);
        }
        page.putField("fellowList", fellowList);

    }

    @Override
    public Site getSite() {
        return Site.me().setCharset("UTF-8").setSleepTime(2000);
    }

    public static void main(String[] args) {
        Spider.create(new FellowListPageProcessor())
                .setDownloader(new HttpClientDownloader())
                .addPipeline(new ConsolePipeline())
                .addUrl("https://awards.acm.org/award_winners?year=&award=157&region=&submit=Submit&isSpecialCategory=")
                .start();
    }
}
