package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.MsaAuthorPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MsaAuthorPaperRepo extends JpaRepository<MsaAuthorPaper, Long> {

    @Query("select count(r) from MsaAuthorPaper r where r.authorId=:authorId and r.paperId=:paperId")
    int countByRelation(@Param("authorId") long authorId, @Param("paperId") long paperId);
}
