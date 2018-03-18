package me.xiaff.crawler.acmfellow.processor.msa;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import me.xiaff.crawler.acmfellow.entity.MsaPaper;
import me.xiaff.crawler.acmfellow.util.HttpRequestFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MsaApiProcessor {
    public List<MsaPaper> getAuthorPapers(long authorId) {
        List<MsaPaper> allPaperList = new ArrayList<>();
        int offset = 0;
        int count = 10000;
        List<MsaPaper> papers = getAuthorPapers(authorId, offset, count);
        allPaperList.addAll(papers);
        offset += count;
        while (papers.size() == count) {
            papers = getAuthorPapers(authorId, offset, count);
            allPaperList.addAll(papers);
            offset += count;
        }
        return allPaperList;
    }

    public List<MsaPaper> getAuthorPapers(long authorId, int offset, int count) {
        List<MsaPaper> papers = new ArrayList<>();

        String params = "expr=Composite(AA.AuId=" + authorId + ")"
                + "&attributes=Id,E.DN,Y,CC,AA.AuId,AA.DAuN,AA.AfN,F.FN,J.JId,J.JN,C.CId,C.CN,DOI"
                + "&orderby=D:desc"
                + "&offset=" + offset
                + "&count=" + count;
        String json = HttpRequestFactory.getMsaApiPage(params);
        JSONObject jsonObject = JSON.parseObject(json);
        if (jsonObject == null) {
            return papers;
        }
//        System.out.println(jsonObject.toJSONString());
        JSONArray entities = jsonObject.getJSONArray("entities");
        if (entities == null) {
            return papers;
        }
        for (Object o : entities) {
            JSONObject entity = (JSONObject) o;
            long id = entity.getLongValue("Id");
            String title = entity.getString("DN");
            int citation = entity.getInteger("CC");
            int year = entity.getInteger("Y");

            List<String> authorList = new ArrayList<>();
            for (Object ao : entity.getJSONArray("AA")) {
                JSONObject authorJson = (JSONObject) ao;
                authorList.add(authorJson.getString("DAuN"));
            }
            String authors = StringUtils.join(authorList, " and ");

            List<String> fieldList = new ArrayList<>();
            JSONArray fArray = entity.getJSONArray("F");
            if (fArray != null) {
                for (Object f : fArray) {
                    JSONObject fieldJson = (JSONObject) f;
                    fieldList.add(fieldJson.getString("FN"));
                }
            }
            String fields = StringUtils.join(fieldList, ";");

            MsaPaper paper = new MsaPaper(id, title, authors, fields, year, citation);

            JSONObject journalJson = entity.getJSONObject("J");
            if (journalJson != null) {
                paper.setJournalId(journalJson.getLongValue("JId"));
                paper.setJournalName(journalJson.getString("JN"));
            }

            JSONObject conferenceJson = entity.getJSONObject("C");
            if (conferenceJson != null) {
                paper.setConferenceId(conferenceJson.getLongValue("CId"));
                paper.setConferenceName(conferenceJson.getString("CN"));
            }
            papers.add(paper);
        }
        return papers;
    }

    public static void main(String[] args) {
        List<MsaPaper> papers = new MsaApiProcessor().getAuthorPapers(2104401652, 0, 10);
        papers.forEach(System.out::println);
    }
}
