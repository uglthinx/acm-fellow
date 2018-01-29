package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.Paper;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaperRepo extends JpaRepository<Paper, Long> {
    Paper findByBibId(String bibId);
}
