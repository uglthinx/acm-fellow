package me.xiaff.crawler.acmfellow.processor;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jbibtex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Dell-lcw on 2017/4/6.
 */
@Component
public class BibProcessor {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private AuthorPaperMapper authorPaperMapper;

    @Transactional
    public void processFile(String fileName, String authorName) throws FileNotFoundException, ParseException {
        List<Paper> paperList = new ArrayList<>();
        List<AuthorPaper> authorPaperList = new ArrayList<>();

        File file = new File(fileName);
        System.out.println(file.exists());
        BibTeXParser bibTeXParser = new BibTeXParser();
        BibTeXDatabase bibTeXDatabase = bibTeXParser.parse(new CharacterFilterReader(new FileReader(fileName)));

        Map<Key, BibTeXEntry> teXStringMap = bibTeXDatabase.getEntries();
        System.out.println("map size: " + teXStringMap.size());
        for (Map.Entry<Key, BibTeXEntry> entry : teXStringMap.entrySet()) {
            Key bibId = entry.getKey();
            BibTeXEntry bibTeXEntry = entry.getValue();

            // find if this paper exists in database
            Paper existPaper = paperMapper.findByBibId(bibId.getValue());
            if (existPaper != null) {
                System.out.println("Paper<" + bibId.getValue() + "> already exists.");
                AuthorPaper authorPaper = new AuthorPaper();
                authorPaper.setAuthor(authorName);
                authorPaper.setBibId(existPaper.getBibId());
                authorPaperList.add(authorPaper);
                continue;
            }

            Map<Key, Value> fieldsMap = bibTeXEntry.getFields();
            if (!fieldsMap.containsKey(BibTeXEntry.KEY_AUTHOR)) {
                continue;
            }
            String titleStr = fieldsMap.get(BibTeXEntry.KEY_TITLE).toUserString();
            LaTeXParser laTeXParser = new LaTeXParser();
            List<LaTeXObject> laTeXObjects = laTeXParser.parse(titleStr);
            String normalTitleStr = new LaTeXPrinter().print(laTeXObjects).replace("\n", " ");

            Paper paper = new Paper();
            paper.setBibId(bibId.getValue());
            String rawAuthorsStr = fieldsMap.get(BibTeXEntry.KEY_AUTHOR).toUserString().replace("\n", " ");
            String authorsStr = new LaTeXPrinter().print(laTeXParser.parse(rawAuthorsStr));
            paper.setAuthor(authorsStr);
            paper.setTitle(normalTitleStr);
            if (fieldsMap.get(BibTeXEntry.KEY_URL) != null) {
                paper.setUrl(fieldsMap.get(BibTeXEntry.KEY_URL).toUserString());
            }
            paper.setYear(fieldsMap.get(BibTeXEntry.KEY_YEAR).toUserString());
            if (fieldsMap.containsKey(BibTeXEntry.KEY_JOURNAL)) {
                paper.setParent(fieldsMap.get(BibTeXEntry.KEY_JOURNAL).toUserString());
            } else if (fieldsMap.containsKey(BibTeXEntry.KEY_BOOKTITLE)) {
                String bookTitleStr = fieldsMap.get(BibTeXEntry.KEY_BOOKTITLE).toUserString();
                String normalBookTitle = new LaTeXPrinter().print(laTeXParser.parse(bookTitleStr)).replace("\n", " ");
                paper.setParent(normalBookTitle);
            }
            // updateCitesById to database
            System.out.println(paper.toString());
            paperList.add(paper);

            AuthorPaper authorPaper = new AuthorPaper();
            authorPaper.setAuthor(authorName);
            authorPaper.setBibId(paper.getBibId());
            authorPaperList.add(authorPaper);
        }


        if (authorPaperList.size() > 0) {
            authorPaperMapper.save(authorPaperList);
        }
        if (paperList.size() > 0) {
            paperMapper.save(paperList);
        }
    }

    public void processDirectory(String directoryName) throws FileNotFoundException, ParseException {
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("<" + directoryName + "> is not a directory!");
            System.exit(1);
        }
        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            String authorName = fileName.substring(0, fileName.lastIndexOf(".bib"));
            System.out.println("Processing <" + fileName + "> by " + authorName + " ...");
            processFile(file.getAbsolutePath(), authorName);
        }
    }

    public void preProcessDirectory(String directoryName) throws IOException {
        File directory = new File(directoryName);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("<" + directoryName + "> is not a directory!");
            System.exit(1);
        }
        for (File file : directory.listFiles()) {
            String fileName = file.getName();
            String authorName = fileName.substring(0, fileName.lastIndexOf(".bib"));
            System.out.println("Processing <" + fileName + "> by " + authorName + " ...");
            String content = IOUtils.toString(new FileInputStream(file), "utf-8");
//            content = content.replaceAll("\\^", "\\\\^").replaceAll("\\\\\\\\\\^", "\\\\^");
//            content = StringUtils.replace(content, "_{", "\\_{");
            content = StringUtils.replace(content,"~","\\~");
            content = StringUtils.replace(content,"\\\\~","\\~");

            content = StringUtils.replace(content,"^","\\^");
            content = StringUtils.replace(content,"\\\\^","\\^");

            content = StringUtils.replace(content,"_","\\_");
            content = StringUtils.replace(content,"\\\\_","\\_");

            content = StringUtils.replace(content,"$","\\$");
            content = StringUtils.replace(content,"\\\\$","\\$");

            IOUtils.write(content, new FileOutputStream(file), "utf-8");

        }
    }
}
