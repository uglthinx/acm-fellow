package me.xiaff.crawler.acmfellow.processor.msa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.xiaff.crawler.acmfellow.entity.MsaAuthor;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MsaSearchProcessor {
    public MsaAuthor searchScholarId(String name) {
        String json = HttpRequestFactory.postMsaSearchPage("@" + name + "@");
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray entitiesInQuery = jsonObject.getJSONArray("entitiesInQuery");
        if (entitiesInQuery.size() == 0) {
            MsaAuthor notFound = new MsaAuthor();
            notFound.setId(0L);
            return notFound;
        }
        JSONObject queryResult = entitiesInQuery.getJSONObject(0);
        long id = queryResult.getJSONObject("entity").getLongValue("id");
        String fullName = queryResult.getString("entityTitle");

        JSONArray fieldsOfStudy = queryResult.getJSONArray("fieldsOfStudy");
        List<String> fields = new ArrayList<>();
        if (fieldsOfStudy != null) {
            fields = fieldsOfStudy.stream().map(jo -> ((JSONObject) jo).getString("lt")).collect(Collectors.toList());
        }
        String fieldsStr = StringUtils.join(fields, ";");

        JSONArray affiliations = queryResult.getJSONArray("affiliations");
        String affiliation = null;
        if (CollectionUtils.isNotEmpty(affiliations)) {
            affiliation = affiliations.getJSONObject(0).getString("lt");
        }
        return new MsaAuthor(id, name, fullName, affiliation, fieldsStr);
    }

    public static void main(String[] args) {
        MsaAuthor msaAuthor = new MsaSearchProcessor().searchScholarId("Abraham, George");
        System.out.println(msaAuthor);
    }
}
