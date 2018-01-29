package me.xiaff.crawler.acmfellow.pipeline;

import me.xiaff.crawler.acmfellow.entity.AcmFellow17;
import me.xiaff.crawler.acmfellow.repo.AcmFellow17Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

@Component
public class FellowListPipeline implements Pipeline {
    @Autowired
    private AcmFellow17Repo acmFellow17Repo;

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<AcmFellow17> fellows = resultItems.get("fellowList");
        for (AcmFellow17 fellow : fellows) {
            if (acmFellow17Repo.findByName(fellow.getName()) == null) {
                acmFellow17Repo.save(fellow);
            }
        }
    }
}
