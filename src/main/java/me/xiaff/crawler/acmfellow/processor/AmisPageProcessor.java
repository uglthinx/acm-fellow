package me.xiaff.crawler.acmfellow.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * AMiner Most Influential Scholar (AMIS) Award 2016
 * https://www.aminer.cn/mostinfluentialscholar
 * Using Selenium WebDriver since it's a dynamic page.
 */
@Component
public class AmisPageProcessor {

    @Autowired
    private AminerScholarRepo aminerScholarRepo;

    private static List<String> getPageUrlList() {
        List<String> pageIdList = new ArrayList<>();
        String jsonStr = new RestTemplate().getForObject("https://api.aminer.org/api/roster/award/overview/offset/0/size/100",
                String.class);
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        int count = jsonObject.getInteger("count");
        System.out.println("count = " + count);
        JSONArray dataArray = jsonObject.getJSONArray("data");
        for (Object o : dataArray) {
            JSONObject data = (JSONObject) o;
            pageIdList.add(data.getString("id"));
        }
        List<String> urlList = pageIdList.stream()
                .map(id -> "https://api.aminer.org/api/roster/award/" + id + "/offset/0/size/100")
                .collect(Collectors.toList());
        System.out.println(urlList);
        return urlList;
    }

    /**
     * Get aminer-id: gender map
     *
     * @return Map<aminer-id ,   gender>
     */
    public void getGenders() throws InterruptedException {
        Map<String, String> idGenderMap = new HashMap<>();
        List<String> urlList = getPageUrlList();
        Thread.sleep(1500);
        for (String url : urlList) {
            String jsonStr = new RestTemplate().getForObject(url, String.class);
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONArray dataArray = jsonObject.getJSONArray("result");
            for (Object o : dataArray) {
                JSONObject data = (JSONObject) o;
                String aminerID = data.getString("id");
                String gender = data.getJSONObject("attr").getString("gender");
                idGenderMap.put(aminerID, gender);
            }
        }

        List<AminerScholar> scholars = aminerScholarRepo.findAll();
        for (AminerScholar scholar : scholars) {
            String aminerId = scholar.getAminerId();
            if (idGenderMap.containsKey(aminerId)) {
                scholar.setGender(idGenderMap.get(aminerId));
                aminerScholarRepo.save(scholar);
                System.out.println(scholar);
            }
        }

    }

    public void run() throws InterruptedException {

        List<String> urlList = getPageUrlList();
        Thread.sleep(1500);
        for (String url : urlList) {
            String jsonStr = new RestTemplate().getForObject(url, String.class);
            JSONObject jsonObject = JSON.parseObject(jsonStr);
            JSONArray dataArray = jsonObject.getJSONArray("result");
            for (Object o : dataArray) {
                JSONObject data = (JSONObject) o;
                AminerScholar scholar = new AminerScholar();
                scholar.setAminerId(data.getString("id"));
                scholar.setFullName(data.getString("name"));
                scholar.setAffiliation(data.getJSONObject("aff").getString("desc"));
                if (data.getJSONArray("pos").size() > 0) {
                    scholar.setPosition(data.getJSONArray("pos").getJSONObject(0).getString("n"));
                }
                scholar.sethIndex(data.getJSONObject("indices").getInteger("h_index"));
                scholar.setPaperNum(data.getJSONObject("indices").getInteger("num_pubs"));
                scholar.setCitationNum(data.getJSONObject("indices").getInteger("num_citation"));
                String tags = data.getJSONArray("tags").stream()
                        .map(jo -> ((JSONObject) jo).getString("t"))
                        .collect(Collectors.joining(";"));
                scholar.setFields(tags);
                scholar.setPage(url);
                System.out.println(scholar);
                aminerScholarRepo.save(scholar);
            }
            Thread.sleep(1500);
        }
    }

}
