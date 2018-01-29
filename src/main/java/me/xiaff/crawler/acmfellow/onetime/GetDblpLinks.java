package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.processor.DblpProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetDblpLinks {
    @Autowired
    private AminerScholarRepo aminerScholarRepo;

    public void run() throws InterruptedException {
        List<AminerScholar> scholars = aminerScholarRepo.findAll();
        List<AminerScholar> filteredScholars = scholars.stream()
                .filter(s -> s.getAcmFellow() == null)
                .filter(s -> s.getIeeeFellow() == null)
                .filter(s -> s.getDblpLink() == null)
                .collect(Collectors.toList());
        DblpProcessor dblpProcessor = new DblpProcessor();
        for (AminerScholar scholar : filteredScholars) {
            String dblpLink = dblpProcessor.searchAuthor(scholar.getName());
            if (dblpLink != null) {
                scholar.setDblpLink(dblpLink);
                aminerScholarRepo.save(scholar);
                System.out.println(scholar);
            }
            Thread.sleep(1000);
        }
    }

}
