package me.kenux.travelog.domain.starpoint.repository;

import me.kenux.travelog.domain.starpoint.entity.StarPoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarPointRepository extends JpaRepository<StarPoint, Long> {
}
