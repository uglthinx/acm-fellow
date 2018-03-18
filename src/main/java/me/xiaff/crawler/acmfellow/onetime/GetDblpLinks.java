package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.entity.FullFellowFeature;
import me.xiaff.crawler.acmfellow.processor.DblpProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import me.xiaff.crawler.acmfellow.repo.FullFellowFeatureRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetDblpLinks {
    private static final Logger log = LoggerFactory.getLogger(GetDblpLinks.class);

    @Autowired
    private AminerScholarRepo aminerScholarRepo;

    @Autowired
    private FullFellowFeatureRepo fullFellowFeatureRepo;

    public void getAminerScholarsLinks() throws InterruptedException {
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

    public void getFellowsLinks() throws InterruptedException {
        List<FullFellowFeature> fellows = fullFellowFeatureRepo.findAll();
        List<FullFellowFeature> filteredFellows = fellows.stream()
                .filter(f -> f.getType2().equals("fellow"))
                .filter(f -> StringUtils.isEmpty(f.getDblpLink()))
                .collect(Collectors.toList());
        DblpProcessor dblpProcessor = new DblpProcessor();
        for (FullFellowFeature fellow : filteredFellows) {
            String dblpLink = dblpProcessor.searchAuthor(fellow.getName());
            if (dblpLink != null) {
                fellow.setDblpLink(dblpLink);
                fullFellowFeatureRepo.save(fellow);
                log.info(fellow.toString());
            }
            Thread.sleep(1000);
        }
    }

}
