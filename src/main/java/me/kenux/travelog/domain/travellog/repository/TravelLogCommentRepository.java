package me.kenux.travelog.domain.travellog.repository;

import me.kenux.travelog.domain.travellog.entity.TravelLogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLogCommentRepository extends JpaRepository<TravelLogComment, Long> {
}
