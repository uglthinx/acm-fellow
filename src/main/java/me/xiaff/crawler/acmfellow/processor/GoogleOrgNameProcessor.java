package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.entity.OrgNameFormation;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class GoogleOrgNameProcessor {


    public static void main(String[] args) {
        OrgNameFormation nameFormation = new GoogleOrgNameProcessor().getFormalOrgName("Grenoble University");
        System.out.println(nameFormation);
    }

    public OrgNameFormation getFormalOrgName(String name) {
        String html = HttpRequestFactory.getGoogleSearchPage(name);
        if (StringUtils.isEmpty(html)) {
            return null;
        }
        Document document = Jsoup.parse(html);
//        System.out.println(document.body().html());
//        System.out.println("====================================================");
        Elements formalNameElements = document.select("div.kno-ecr-pt span:nth-child(1)");
        if (formalNameElements.isEmpty()) {
            return null;
        }

        String formalName = formalNameElements.get(0).text();
        OrgNameFormation orgNameFormation = new OrgNameFormation(name, formalName, "Google");
        Elements infoElements = document.select("div.kp-hc  div.mod:nth-child(2) span");
        if (!infoElements.isEmpty()) {
            orgNameFormation.setInfo(infoElements.get(0).text());
        }
        return orgNameFormation;
    }

}
