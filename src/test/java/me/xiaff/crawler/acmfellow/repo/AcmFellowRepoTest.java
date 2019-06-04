package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.AcmFellow;
import me.xiaff.crawler.acmfellow.pipeline.FellowListPipeline;
import me.xiaff.crawler.acmfellow.pipeline.FellowPagePipeline;
import me.xiaff.crawler.acmfellow.processor.AcmFellowPageProcessor;
import me.xiaff.crawler.acmfellow.processor.FellowListPageProcessor;
import me.xiaff.crawler.acmfellow.util.HttpClientDownloader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AcmFellowRepoTest {

    @Autowired
    private AcmFellowRepo acmFellowRepo;

    @Test
    public void findByName() {
        System.out.println(acmFellowRepo.findByName("Druin, Allison"));
    }

    @Autowired
    private FellowListPipeline fellowListPipeline;

    @Test
    public void downloadAcmFellow() {
        Spider.create(new FellowListPageProcessor())
                .setDownloader(new HttpClientDownloader())
                .addPipeline(fellowListPipeline)
                .addUrl("https://awards.acm.org/fellows/award-winners")
                .run();
    }

    @Test
    public void downloadAcmDistinguished() {
        Spider.create(new FellowListPageProcessor())
                .addPipeline(fellowListPipeline)
                .setDownloader(new HttpClientDownloader())
                .addUrl("https://awards.acm.org/award_winners?year=&award=157&region=&submit=Submit&isSpecialCategory=")
                .run();
    }

    @Autowired
    private FellowPagePipeline fellowPagePipeline;

    @Test
    public void downloadAcmFellowCitation() {
        List<AcmFellow> fellows = acmFellowRepo.findAll();
        List<String> urlList = fellows.stream()
                .filter(f -> f.getDescription() == null)
                .filter(f ->f.getType2().equals("fellow"))
                .map(AcmFellow::getUrl)
                .collect(Collectors.toList());
        String[] urls = new String[urlList.size()];
        urls = urlList.toArray(urls);
        Spider.create(new AcmFellowPageProcessor())
                .setDownloader(new HttpClientDownloader())
                .addPipeline(fellowPagePipeline)
                .addUrl(urls)
                .run();
    }

}