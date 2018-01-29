package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.AcmFellow17;
import me.xiaff.crawler.acmfellow.onetime.RecognizeFellow;
import me.xiaff.crawler.acmfellow.pipeline.FellowListPipeline;
import me.xiaff.crawler.acmfellow.pipeline.FellowPagePipeline;
import me.xiaff.crawler.acmfellow.processor.AcmFellowPageProcessor;
import me.xiaff.crawler.acmfellow.processor.FellowListPageProcessor;
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
public class AcmFellow17RepoTest {

    @Autowired
    private AcmFellow17Repo acmFellow17Repo;

    @Test
    public void findByName() {
        System.out.println(acmFellow17Repo.findByName("Druin, Allison"));
    }

    @Autowired
    private FellowListPipeline fellowListPipeline;

    @Test
    public void downloadAcmFellow() {
        Spider.create(new FellowListPageProcessor())
                .addPipeline(fellowListPipeline)
                .addUrl("https://awards.acm.org/fellows/award-winners")
                .run();
    }

    @Test
    public void downloadAcmDistinguished() {
        Spider.create(new FellowListPageProcessor())
                .addPipeline(fellowListPipeline)
                .addUrl("https://awards.acm.org/award_winners?year=&award=157&region=&submit=Submit&isSpecialCategory=")
                .run();
    }


    @Autowired
    private FellowPagePipeline fellowPagePipeline;

    @Test
    public void downloadAcmFellowCitation() {
        List<AcmFellow17> fellows = acmFellow17Repo.findAll();
        List<String> urlList = fellows.stream()
                .filter(f -> f.getDescription() == null)
                .filter(f ->f.getType2().equals("distinguished"))
                .map(AcmFellow17::getUrl)
                .collect(Collectors.toList());
        String[] urls = new String[urlList.size()];
        urls = urlList.toArray(urls);
        Spider.create(new AcmFellowPageProcessor())
                .addPipeline(fellowPagePipeline)
                .addUrl(urls)
                .run();
    }

}