package me.kenux.travelog.domain.travellog.repository;

import me.kenux.travelog.domain.travellog.entity.TravelLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long>, TravelLogCustomRepository {

}
