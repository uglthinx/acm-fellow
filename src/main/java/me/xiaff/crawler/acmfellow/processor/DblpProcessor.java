package me.xiaff.crawler.acmfellow.processor;

import me.xiaff.crawler.acmfellow.entity.Paper;
import org.jbibtex.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DblpProcessor {
    public String searchAuthor(String name) {
        String url = "http://dblp.uni-trier.de/search/author/inc?q={name}&s=1";
        RestTemplate restTemplate = new RestTemplate();
        String html = restTemplate.getForObject(url, String.class, name);
        Document doc = Jsoup.parse(html);

        Elements links = doc.select("#completesearch-authors > div > ul > li > a");
        for (Element link : links) {
            String spanText = link.select("span").text()
                    .replace(" ", "")
                    .replace(".", "");
            Elements marks = link.select("mark");
            String markText = marks.stream().map(Element::text).collect(Collectors.joining());
//            System.out.println("span: " + spanText);
//            System.out.println("mark: " + markText);
            if (spanText.equals(markText)) {
                return link.attr("href");
            }
        }

        return null;
    }

    public List<Paper> getBibs(String url) throws ParseException {
        System.out.println("URL=" + url);
        List<Paper> papers = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String html = restTemplate.getForObject(url, String.class);

        BibTeXParser parser = new BibTeXParser();
        BibTeXDatabase bibData = parser.parse(new StringReader(html));

        Map<Key, BibTeXEntry> entries = bibData.getEntries();
        for (Map.Entry<Key, BibTeXEntry> entry : entries.entrySet()) {
            Paper paper = new Paper();
            String dblpId = entry.getKey().getValue();
            BibTeXEntry bibTeXEntry = entry.getValue();

            Key entryType = bibTeXEntry.getType();
            if (entryType.equals(BibTeXEntry.TYPE_ARTICLE)) {
                paper.setParent(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_JOURNAL)));
            } else if (entryType.equals(BibTeXEntry.TYPE_INPROCEEDINGS)) {
                paper.setParent(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_BOOKTITLE)));
            } else if (entryType.equals(BibTeXEntry.TYPE_BOOK)) {
                paper.setParent(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_PUBLISHER)));
            } else if (entryType.equals(BibTeXEntry.TYPE_PHDTHESIS)) {
                paper.setParent(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_SCHOOL)));
            } else if (entryType.equals(BibTeXEntry.TYPE_TECHREPORT)) {
                paper.setParent(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_SCHOOL)));
            } else {
                continue;
            }
            paper.setType(entryType.getValue());

            paper.setBibId(dblpId);
            paper.setTitle(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_TITLE)));
            paper.setAuthor(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_AUTHOR)));
            paper.setYear(toPlainText(bibTeXEntry.getField(BibTeXEntry.KEY_YEAR)));
            paper.setUrl(toPlainText(bibTeXEntry.getField(new Key("biburl"))));

            System.out.println(paper);
            papers.add(paper);
        }
        return papers;
    }


    private String toPlainText(Value latexValue) {
        if (latexValue == null) {
            return null;
        }
        try {
            List<LaTeXObject> latexObjects = new LaTeXParser().parse(latexValue.toUserString());
            return new LaTeXPrinter().print(latexObjects)
                    .replaceAll("\n+", " ")
                    .replaceAll(" +"," ");
        } catch (ParseException e) {
            e.printStackTrace();
            return latexValue.toUserString().replaceAll("\n+", "");
        }
    }

    public static void main(String[] args) throws ParseException {
//        System.out.println(new DblpProcessor().searchAuthor("Marti A. Hearst"));
//        System.out.println(new DblpProcessor().searchAuthor("Su, Jian"));
        new DblpProcessor().getBibs("http://dblp.uni-trier.de/pers/tb2/e/Ester:Martin");
    }
}
