package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRatingRepository extends JpaRepository<BookRate, Long>, BookRatingCustomRepository {

    @Query(value = "select br from BookRate br where br.book.id = :bookId")
    List<BookRate> findByBookId(Long bookId);
}
