package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.GScholar;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GScholarRepo extends JpaRepository<GScholar, String> {
    GScholar findByName(String name);
}
