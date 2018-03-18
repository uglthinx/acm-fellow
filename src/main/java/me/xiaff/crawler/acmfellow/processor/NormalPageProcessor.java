package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.util.HtmlUtils;
import me.xiaff.crawler.acmfellow.util.NerUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 普通网页处理器，从中抽取网页正文内容，并进行命名实体识别
 * Created by Lu Chenwei on 2017/7/14.
 */
public class NormalPageProcessor {

    public static final Logger logger = LoggerFactory.getLogger(NormalPageProcessor.class);
    private WebDriver webDriver;

    public NormalPageProcessor(WebDriver webDriver) {
        this.webDriver = webDriver;
    }


    /**
     * 获取网页中作者的PhD学校和年份
     *
     * @param url 网页URL
     * @return PhD学校和年份
     */
    public Pair<String, String> getPhdSchoolAndYear(String url) {
        try {
            webDriver.get(url);
            Thread.sleep(2000L);
        } catch (TimeoutException e) {
            return null;
        } catch (InterruptedException ignore) {
        }
        String pageSource = webDriver.getPageSource();
        String text = HtmlUtils.getText(pageSource);
        return NerUtils.getPhdSchoolAndYear(text);
    }
}
