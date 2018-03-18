package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.entity.AuthorPaper;
import me.xiaff.crawler.acmfellow.entity.Paper;
import me.xiaff.crawler.acmfellow.processor.DblpProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import me.xiaff.crawler.acmfellow.repo.AuthorPaperRepo;
import me.xiaff.crawler.acmfellow.repo.PaperRepo;
import org.jbibtex.ParseException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DownloadDblpPapers {
    @Resource
    private AminerScholarRepo aminerScholarRepo;

    @Resource
    private PaperRepo paperRepo;

    @Resource
    private AuthorPaperRepo authorPaperRepo;

    private void addPaperAuthorRelations(String authorId, List<Paper> papers) {
        List<AuthorPaper> authorPaperList = new ArrayList<>();
        for (Paper paper : papers) {
            if (authorPaperRepo.countByRelation(authorId, paper.getBibId()) > 0) {
                continue;
            }
            authorPaperList.add(new AuthorPaper(authorId, paper.getBibId(), Integer.parseInt(paper.getYear())));
        }
        authorPaperRepo.save(authorPaperList);
    }

    public void run() throws Exception {
        List<AminerScholar> scholars = aminerScholarRepo.findAll();
        List<AminerScholar> filteredScholars = scholars.stream()
                .filter(s -> s.getDblpLink() != null)
                .filter(s -> s.getCrawled() == null || !s.getCrawled())
                .collect(Collectors.toList());
        DblpProcessor dblpProcessor = new DblpProcessor();
        for (AminerScholar scholar : filteredScholars) {
            System.err.println(scholar);
            List<Paper> papers = dblpProcessor.getBibs(scholar.getDblpLink().replace("/hd/", "/tb2/"));
            // Update scholar crawled state
            scholar.setCrawled(true);
            aminerScholarRepo.save(scholar);
            // Add author-paper relations
            addPaperAuthorRelations(scholar.getName(), papers);
            // save new papers
            List<Paper> newPapers = papers.stream()
                    .filter(p -> paperRepo.findByBibId(p.getBibId()) == null)
                    .collect(Collectors.toList());
            paperRepo.save(newPapers);
        }
    }

    public void downloadExtraLinks(String link, String name) throws ParseException {
        DblpProcessor dblpProcessor = new DblpProcessor();
        List<Paper> papers = dblpProcessor.getBibs(link.replace("/hd/", "/tb2/"));
        addPaperAuthorRelations(name, papers);
        List<Paper> newPapers = papers.stream()
                .filter(p -> paperRepo.findByBibId(p.getBibId()) == null)
                .collect(Collectors.toList());
        paperRepo.save(newPapers);
    }
}
