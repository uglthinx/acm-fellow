package me.xiaff.crawler.acmfellow.onetime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetDblpLinksTest {

    @Autowired
    private GetDblpLinks getDblpLinks;

    @Test
    public void run() throws Exception {
        getDblpLinks.run();
    }
}