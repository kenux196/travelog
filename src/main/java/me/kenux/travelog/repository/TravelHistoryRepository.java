package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelHistoryRepository extends JpaRepository<TravelHistory, Long> {
}
