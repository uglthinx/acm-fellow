package me.xiaff.crawler.acmfellow.repo;

import me.xiaff.crawler.acmfellow.entity.OrgNameFormation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgNameFormationRepo extends JpaRepository<OrgNameFormation, Long> {
    OrgNameFormation findByOldName(String school);
}
