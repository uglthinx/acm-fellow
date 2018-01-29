package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.AcmFellow17;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AcmFellow17Repo extends JpaRepository<AcmFellow17, Long> {

    AcmFellow17 findByName(@Param("name") String name);

    AcmFellow17 findByUrl(@Param("url") String url);
}
