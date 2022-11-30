package me.kenux.travelog.repository;

import me.kenux.travelog.domain.TravelLogComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelLogCommentRepository extends JpaRepository<TravelLogComment, Long> {
}
