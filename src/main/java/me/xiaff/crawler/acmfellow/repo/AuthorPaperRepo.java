package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.AuthorPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorPaperRepo extends JpaRepository<AuthorPaper, Long> {
    @Query("select count(ap) from AuthorPaper ap where ap.authorName=:author and ap.bibId=:bib")
    long countByRelation(@Param("author") String authorId, @Param("bib") String bibId);
}
