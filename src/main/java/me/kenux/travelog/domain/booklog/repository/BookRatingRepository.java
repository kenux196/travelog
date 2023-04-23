package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRatingRepository extends JpaRepository<BookRating, Long> {

    @Query(value = "select br from BookRating br where br.book.id = :bookId")
    List<BookRating> findByBookId(Long bookId);
}
