package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.AcmFellow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AcmFellowRepo extends JpaRepository<AcmFellow, Long> {

    AcmFellow findByName(@Param("name") String name);

    AcmFellow findByUrl(@Param("url") String url);
}
