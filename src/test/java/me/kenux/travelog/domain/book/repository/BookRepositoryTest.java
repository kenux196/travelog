package me.kenux.travelog.domain.book.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.book.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends BaseRepositoryConfig {

    @PersistenceContext
    EntityManager em;
    @Autowired
    BookRepository bookRepository;

    @Test
    void existBookByIsbn() {
        // given
        final Book book = Book.builder()
                .title("book1")
                .authors("author1")
                .isbn("1234")
                .publisher("publisher1")
                .publishedDate(LocalDate.of(2023, 1, 1))
                .build();
        bookRepository.save(book);

        // when
        boolean result = bookRepository.existsBookByIsbn(book.getIsbn());

        // then
        assertThat(result).isTrue();
    }

    @Test
    void findAllByIsbn() {
        final Book book = Book.builder()
                .title("book1")
                .authors("author1")
                .isbn("1234")
                .publisher("publisher1")
                .publishedDate(LocalDate.of(2023, 1, 1))
                .build();
        bookRepository.save(book);
        em.clear();

        final List<Book> result = bookRepository.findAllByIsbn(Collections.singletonList(book.getIsbn()));

        assertThat(result).hasSize(1);

    }
}