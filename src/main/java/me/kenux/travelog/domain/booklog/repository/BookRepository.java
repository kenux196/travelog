package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long>, BookCustomRepository {
    boolean existsBookByIsbn(String isbn);

    @Query(value = "select b from Book b where b.isbn in :isbn")
    List<Book> findAllByIsbn(@Param("isbn") List<String> isbn);

    @Query(value = "select b from Book b where b.isbn = :isbn")
    Optional<Book> findBookByIsbn(String isbn);

}
