package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.FullFellowFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FullFellowFeatureRepo extends JpaRepository<FullFellowFeature, Long> {

    @Query("select f from FullFellowFeature f where f.type2='fellow' and (f.pubNum<=10 or f.waitYear<=7)")
    List<FullFellowFeature> findUnsatisfiedFellows();
}
