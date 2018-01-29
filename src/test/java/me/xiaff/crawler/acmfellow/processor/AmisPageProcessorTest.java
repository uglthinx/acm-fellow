package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.onetime.RecognizeFellow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AmisPageProcessorTest {

    @Autowired
    private AmisPageProcessor amisPageProcessor;


    @Test
    public void run() throws Exception{
        amisPageProcessor.run();
    }

    @Test
    public void getGenders() throws Exception{
        amisPageProcessor.getGenders();
    }


}