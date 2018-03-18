package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.entity.FullFellowFeature;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import me.xiaff.crawler.acmfellow.repo.FullFellowFeatureRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecognizeFellow {

    @Resource
    private FullFellowFeatureRepo fffRepo;

    @Resource
    private AminerScholarRepo aminerScholarRepo;

    private boolean isSimilarName(String name1, String name2) {
        return (name1.startsWith(name2) || name2.startsWith(name1));
    }


    public void recognize() {
        List<FullFellowFeature> fellows = fffRepo.findAll();
        List<FullFellowFeature> filteredFellows = fellows.stream().filter(f -> f.getType2().equals("fellow"))
                .collect(Collectors.toList());

        List<AminerScholar> aminerScholars = aminerScholarRepo.findAll();
        for (AminerScholar scholar : aminerScholars) {
            String name = scholar.getName();
            for (FullFellowFeature fellow : filteredFellows.stream()
                    .filter(f -> f.getType1().equals("acm")).collect(Collectors.toList())) {
                if (isSimilarName(name, fellow.getName())) {
                    scholar.setAcmFellow(true);
                    System.out.println("ACM Fellow: " + scholar);
                }
            }

            for (FullFellowFeature fellow : filteredFellows.stream()
                    .filter(f -> f.getType1().equals("ieee")).collect(Collectors.toList())) {
                if (isSimilarName(name, fellow.getName())) {
                    scholar.setIeeeFellow(true);
                    System.out.println("IEEE Fellow: " + scholar);
                }
            }
        }

        aminerScholarRepo.save(aminerScholars);
    }
}
