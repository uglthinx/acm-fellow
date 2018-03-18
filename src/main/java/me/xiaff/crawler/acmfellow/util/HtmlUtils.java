package me.xiaff.crawler.acmfellow.util;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Lu Chenwei on 2017/7/13.
 */
public class HtmlUtils {
    /**
     * 去掉字符串里面的html标签
     *
     * @param htmlStr html string
     * @return text string
     */
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
        String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        // 去掉空格，换行符，制表符
        htmlStr = htmlStr.replaceAll("[\r|\n]+", ". ").replaceAll("\t", "").replaceAll("  ", "").replaceAll("&nbsp;", "");
        return htmlStr.trim(); // 返回文本字符串
    }

    public static String getText(String html) {
        try {
//            return CommonExtractors.KEEP_EVERYTHING_EXTRACTOR.getText(html);
            return ArticleExtractor.INSTANCE.getText(html);
        } catch (BoilerpipeProcessingException e) {
            e.printStackTrace();
            return HtmlUtils.delHTMLTag(html);
        }
    }
}
