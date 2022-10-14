package me.kenux.travelog.repository;

import me.kenux.travelog.domain.StarPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarPointRepository extends JpaRepository<StarPoint, Long> {
}
