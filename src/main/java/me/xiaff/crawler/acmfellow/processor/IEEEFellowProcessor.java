package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.entity.IeeeCsFellow;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class IEEEFellowProcessor {
    public void downloadFellows(String gender) {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("PageNum", "1");
        form.add("inputFilterJSON", "{\"society\":\"MEMC016:IEEE+Computer+Society+Membership\",\"gender\":\"Men\",\"sortOnList\":[{\"sortByField\":\"fellow.lastName\",\"sortType\":\"ASC\"}],\"requestedPageNumber\":\"1\",\"typeAhead\":false}");
        HttpEntity<?> entity = new HttpEntity<Object>(form);

        ResponseEntity<String> html = restTemplate.postForEntity("https://services27.ieee.org/fellowsdirectory/getpageresultsdesk.html",
                entity, String.class);
        Document document = Jsoup.parse(html.getBody());
        System.out.println(document.body());
        System.out.println("=====================================================================");
        Elements rows = document.select("div.tr");
        for (Element row : rows) {
//            System.out.println(row.text());
            String name = row.select(".name").text();
            String region = row.select(".region").text();
            int year = Integer.parseInt(row.select(".class").text());
            String description = row.select(".citation").text();
            String category = row.select(".category").text();

            IeeeCsFellow fellow = new IeeeCsFellow();
            fellow.setName(name);
            fellow.setGender(gender);
            fellow.setSelectYear(year);
            fellow.setDescription(description);
            fellow.setCategory(category);
            fellow.setRegion(region);
            System.out.println(fellow);

        }


    }

    public static void main(String[] args) {
        new IEEEFellowProcessor().downloadFellows("Men");
    }
}
