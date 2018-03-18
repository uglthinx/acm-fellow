package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.MsaAuthor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MsaAuthorRepo extends JpaRepository<MsaAuthor, Long> {
    MsaAuthor findByName(String name);

    List<MsaAuthor> findByConsistentIsNull();
}
