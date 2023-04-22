package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookStarPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookStartPointRepository extends JpaRepository<BookStarPoint, Long> {

    @Query(value = "select p from BookStarPoint p where p.book.id = :bookId")
    List<BookStarPoint> findByBookId(Long bookId);
}
