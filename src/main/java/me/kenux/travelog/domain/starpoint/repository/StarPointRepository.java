package me.kenux.travelog.domain.starpoint.repository;

import me.kenux.travelog.domain.booklog.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StarPointRepository extends JpaRepository<BookRating, Long> {
}
