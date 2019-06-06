package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.AminerScholar;
import me.xiaff.crawler.acmfellow.entity.OrgNameFormation;
import me.xiaff.crawler.acmfellow.processor.google.GoogleOrgNameProcessor;
import me.xiaff.crawler.acmfellow.repo.AminerScholarRepo;
import me.xiaff.crawler.acmfellow.repo.OrgNameFormationRepo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FindGoogleFormalName {
    private GoogleOrgNameProcessor orgNameProcessor = new GoogleOrgNameProcessor();
    private static final Logger log = LoggerFactory.getLogger(FindGoogleFormalName.class);

//    @Resource
//    private FellowRepo fellowRepo;

    @Resource
    private AminerScholarRepo aminerScholarRepo;
    @Resource
    private OrgNameFormationRepo orgNameFormationRepo;

    //    @Override
    public void run(String... args) throws Exception {
        System.out.println("============================================");
        System.out.println("============FindGoogleFormalName============");
        System.out.println("============================================");

        List<AminerScholar> fellows = aminerScholarRepo.findAll();
        for (AminerScholar fellow : fellows) {
            String school = fellow.getPhdSchool();
            if (StringUtils.isEmpty(school)) {
                continue;
            }
            if (orgNameFormationRepo.findByOldName(school) != null) {
                continue;
            }
            log.info("{}@[{}] ...", fellow.getName(), school);
            OrgNameFormation nameFormation = orgNameProcessor.getFormalOrgName(school);
            if (nameFormation != null) {
                log.info(nameFormation.toString());
                orgNameFormationRepo.save(nameFormation);
            }
            Thread.sleep(3000);
        }
    }
}
