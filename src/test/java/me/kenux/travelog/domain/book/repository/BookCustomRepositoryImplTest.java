package me.kenux.travelog.domain.book.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.book.entity.Book;
import me.kenux.travelog.domain.book.repository.dto.BookSearchCond;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookCustomRepositoryImplTest extends BaseRepositoryConfig {

    private final String baseTitle = "test book";
    private final String author = "kim, lee, yun";
    private final String publisher = "publisher";
    private final String isbn = "isbn-123";

    @Autowired
    BookRepository bookRepository;

    @BeforeEach
    void setup() {
        final Book newBook = Book.createNewBook(
                baseTitle, author, isbn, LocalDate.of(2023, 1, 1), publisher);

        bookRepository.save(newBook);
    }

    @Test
    void findByCondition_contains_title() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle("test");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findByCondition_not_contains_title() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle("not exist book");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(0);
    }

    @Test
    void findByCondition_contains_author_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setAuthor("kim");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findByCondition_equal_publisher_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setPublisher(publisher);

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findByCondition_equal_isbn_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setIsbn(isbn);

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void findByCondition_allCondition_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle("test");
        cond.setPublisher(publisher);
        cond.setIsbn(isbn);
        cond.setAuthor("kim");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(1);
    }

}