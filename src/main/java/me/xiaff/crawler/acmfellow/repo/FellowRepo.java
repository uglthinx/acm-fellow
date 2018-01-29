package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.FellowDO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FellowRepo extends JpaRepository<FellowDO, Long> {
}
