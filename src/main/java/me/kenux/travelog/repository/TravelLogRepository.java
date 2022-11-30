package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long> {
}
