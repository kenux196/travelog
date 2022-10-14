package me.kenux.travelog.repository;

import me.kenux.travelog.domain.SiteInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiteInfoRepository extends JpaRepository<SiteInfo, Long> {
}
