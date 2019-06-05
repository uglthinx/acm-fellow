package me.xiaff.crawler.acmfellow.processor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class IEEEFellowProcessorTest {
    @Autowired
    private IEEEFellowProcessor ieeeFellowProcessor;

    @Test
    public void run() throws Exception{
        ieeeFellowProcessor.downloadFellows("Men", "2018", "2019");
        ieeeFellowProcessor.downloadFellows("Women", "2018", "2019");
        System.out.println("===================Finished======================");
    }
}
