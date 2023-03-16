package me.kenux.travelog.domain.book.repository;

import me.kenux.travelog.domain.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
