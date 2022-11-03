package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelHistoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelHistoryCommentRepository extends JpaRepository<TravelHistoryComment, Long> {
}
