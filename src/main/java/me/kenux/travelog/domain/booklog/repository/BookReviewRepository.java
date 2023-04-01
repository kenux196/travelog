package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookReviewRepository extends JpaRepository<BookReview, Long>, BookReviewCustomRepository {

    @Query(value = "select br from BookReview br where br.book.id = :bookId")
    List<BookReview> findByBookId(@Param("bookId") Long bookId);

    @Query(value = "select br from BookReview br where br.member.id = :memberId")
    List<BookReview> findByMemberId(@Param("memberId") Long memberId);
}
