package me.xiaff.crawler.acmfellow.crawler;

import me.xiaff.crawler.acmfellow.entity.FellowDO;
import me.xiaff.crawler.acmfellow.repo.FellowRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DblpBibDownloadTest {

    @Autowired
    private DBLPDownloader dblpDownloader;

    @Autowired
    private FellowRepo fellowRepo;

    @Test
    public void download() throws IOException {
//        List<FellowDO> fellowDOList = fellowRepo.findAll();
//        for (FellowDO fellowDO : fellowDOList) {
//            dblpDownloader.getAuthorPapers(fellowDO.getName());
//        }
        dblpDownloader.getAuthorPapers("Mohamed-Slim Alouini");
    }
}
