package me.xiaff.crawler.acmfellow.pipeline;

import me.xiaff.crawler.acmfellow.entity.AcmFellow;
import me.xiaff.crawler.acmfellow.repo.AcmFellowRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

@Component
public class FellowPagePipeline implements Pipeline {
    @Autowired
    private AcmFellowRepo repo;

    @Override
    public void process(ResultItems resultItems, Task task) {
        String citation = resultItems.get("citation");
        String url = resultItems.get("url");

        AcmFellow fellow = repo.findByUrl(url);
        if (fellow != null) {
            fellow.setDescription(citation);
            repo.save(fellow);
        }
    }
}
