package me.xiaff.crawler.acmfellow.processor;

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

public class AcmFellowPageProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        Document document = Jsoup.parse(page.getHtml().toString());
        Elements sections = document.select("section.awards-winners__citation");
        for (Element section : sections) {
            if (section.text().contains("ACM Distinguished Member")) {
                String citation = section.select("p.awards-winners__citation-short").text();
                page.putField("url", page.getRequest().getUrl());
                page.putField("citation", citation);
            }
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setCharset("UTF-8").setSleepTime(2000);
    }

    public static void main(String[] args) {
        Spider.create(new AcmFellowPageProcessor())
                .addPipeline(new ConsolePipeline())
                .setDownloader(new HttpClientDownloader())
                .addUrl("https://awards.acm.org/award_winners/kedem_1284975")
                .run();
    }
}
