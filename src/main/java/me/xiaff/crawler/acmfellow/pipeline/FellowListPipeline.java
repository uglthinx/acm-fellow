package me.xiaff.crawler.acmfellow.pipeline;

import me.xiaff.crawler.acmfellow.entity.AcmFellow;
import me.xiaff.crawler.acmfellow.repo.AcmFellowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class FellowListPipeline implements Pipeline {
    @Autowired
    private AcmFellowRepo acmFellowRepo;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<AcmFellow> fellows = resultItems.get("fellowList");
        for (AcmFellow fellow : fellows) {
            if (acmFellowRepo.findByName(fellow.getName()) == null) {
                acmFellowRepo.save(fellow);
            }
        }
    }
}
