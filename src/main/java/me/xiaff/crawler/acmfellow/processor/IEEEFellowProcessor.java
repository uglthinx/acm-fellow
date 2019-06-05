package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.entity.FellowDO;
import me.xiaff.crawler.acmfellow.entity.IeeeFellow;
import me.xiaff.crawler.acmfellow.repo.IeeeFellowRepo;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class IEEEFellowProcessor {

    @Resource
    private IeeeFellowRepo ieeeFellowRepo;

    public void downloadFellows(String gender, String startYear, String endYear) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        int pageNum = 1;
        form.add("selectedJSON", "{\"alpha\":\"ALL\",\"menu\":\"CHRONOLOGICAL\",\"gender\":\"Men\",\"currPageNum\":1,\"breadCrumbs\":[{\"breadCrumb\":\"Chronological\"}],\"beginYr\":\"2018\",\"endYr\":\"2018\",\"helpText\":\"Drag either up-arrow to view a timespan of Fellows or select a specific year in the drop-down list.\"}");
        form.add("inputFilterJSON", "{\"yearRange\":{\"beginYear\":\"" + startYear + "\",\"endYear\":\"" + endYear + "\"},\"gender\":\"" + gender + "\",\"sortOnList\":[{\"sortByField\":\"fellow.yrElevation\",\"sortType\":\"ASC\"},{\"sortByField\":\"fellow.lastName\",\"sortType\":\"ASC\"}],\"requestedPageNumber\":\"1\",\"typeAhead\":false}");
        List<IeeeFellow> fellowList = new ArrayList<>();
        while (true) {
            form.set("PageNum", String.valueOf(pageNum++));
            HttpEntity<?> entity = new HttpEntity<Object>(form, HttpRequestFactory.getIeeeHttpHeaders());

            ResponseEntity<String> html = restTemplate.postForEntity("https://services27.ieee.org/fellowsdirectory/getpageresultsdesk.html",
                    entity, String.class);
            Document document = Jsoup.parse(html.getBody());
            System.out.println("=====================================================================");
            Elements rows = document.select("div.tr");
            if (rows.size() == 0) {
                break;
            }
            for (Element row : rows) {
                String name = row.select(".name").text();
                String region = row.select(".region").text();
                int year = Integer.parseInt(row.select(".class").text());
                String description = row.select(".citation").text();
                String category = row.select(".category").text();

                IeeeFellow fellow = new IeeeFellow();
                fellow.setName(name);
                fellow.setGender(gender);
                fellow.setSelectYear(year);
                fellow.setDescription(description);
                fellow.setCategory(category);
                fellow.setRegion(region);
                fellowList.add(fellow);
            }
        }
        ieeeFellowRepo.save(fellowList);
    }
}
