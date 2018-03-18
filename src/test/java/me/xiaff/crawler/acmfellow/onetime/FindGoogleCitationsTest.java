package me.xiaff.crawler.acmfellow.onetime;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FindGoogleCitationsTest {

    @Autowired
    private FindGoogleCitations findGoogleCitations;

    @Test
    public void finExtraPaperCites() throws  Exception{
        System.setProperty("java.net.preferIPv6Addresses", "true");
        findGoogleCitations.finExtraPaperCites();
    }
}