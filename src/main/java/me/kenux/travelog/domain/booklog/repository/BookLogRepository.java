package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookLogRepository extends JpaRepository<BookLog, Long>, BookLogCustomRepository {

    @Query(value = "select bl from BookLog bl join fetch bl.member join fetch bl.book where bl.member.id = :memberId")
    List<BookLog> findByMember(Long memberId);

    @Query(value = "select bl from BookLog bl join fetch bl.member join fetch bl.book where bl.book.id = :bookId")
    List<BookLog> findByBook(Long bookId);
}
