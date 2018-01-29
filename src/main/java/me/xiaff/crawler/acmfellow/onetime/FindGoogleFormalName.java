package me.xiaff.crawler.acmfellow.onetime;

import me.xiaff.crawler.acmfellow.entity.FellowDO;
import me.xiaff.crawler.acmfellow.entity.OrgNameFormation;
import me.xiaff.crawler.acmfellow.processor.GoogleOrgNameProcessor;
import me.xiaff.crawler.acmfellow.repo.FellowRepo;
import me.xiaff.crawler.acmfellow.repo.OrgNameFormationRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class FindGoogleFormalName implements CommandLineRunner {
    private GoogleOrgNameProcessor orgNameProcessor = new GoogleOrgNameProcessor();
    private static final Logger log = LoggerFactory.getLogger(FindGoogleCitations.class);

    @Resource
    private FellowRepo fellowRepo;
    @Resource
    private OrgNameFormationRepo orgNameFormationRepo;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("============================================");
        System.out.println("============FindGoogleFormalName============");
        System.out.println("============================================");

        List<FellowDO> fellows = fellowRepo.findAll();
        for (FellowDO fellow : fellows) {
            String school = fellow.getFinalPhdSchool();
            if(orgNameFormationRepo.findByOldName(school)!=null){
                continue;
            }
            log.info("{}@[{}] ...", fellow.getName(), school);
            OrgNameFormation nameFormation = orgNameProcessor.getFormalOrgName(school);
            if (nameFormation != null) {
                log.info(nameFormation.toString());
                orgNameFormationRepo.save(nameFormation);
            }
        }
    }
}
