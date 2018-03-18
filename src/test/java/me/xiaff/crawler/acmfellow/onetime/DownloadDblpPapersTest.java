package me.xiaff.crawler.acmfellow.onetime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownloadDblpPapersTest {

    @Autowired
    private DownloadDblpPapers downloadDblpPapers;

    @Test
    public void run() throws Exception {
        downloadDblpPapers.run();
    }

    @Test
    public void downloadExtraLinks() throws Exception {
        String link = "http://dblp.uni-trier.de/pers/hd/p/Preas:Bryan";
        String name = "Preas, Bryan";
        downloadDblpPapers.downloadExtraLinks(link, name);
    }
}