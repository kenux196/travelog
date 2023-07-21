package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.dto.BookSearchCond;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookCustomRepositoryImplTest extends RepositoryTestConfigure {

    private final String BASE_TITLE = "Funny Book";
    private final String AUTHOR = "kim, lee, yun";
    private final String PUBLISHER = "publisher";
    private final String ISBN = "isbn-123";
    private final LocalDate basePublishDate = LocalDate.of(2023, 1, 1);
    private final int totalBookCount = 100;

    @Autowired
    BookRepository bookRepository;
    @Autowired
    EntityManager em;

    @BeforeEach
    void setup() {
        prepareTestBookData();
    }

    private void prepareTestBookData() {
        List<Book> newBooks = new ArrayList<>();
        for (int i = 0; i < totalBookCount; i++) {
            final String title = String.format("%s %d", BASE_TITLE, i);
            final LocalDate publishDate = basePublishDate.plusWeeks(1L);
            final Book newBook = Book.createNewBook(title, AUTHOR, ISBN, publishDate, PUBLISHER);
            newBooks.add(newBook);
        }
        bookRepository.saveAll(newBooks);
        em.flush();
        em.clear();
    }

    @Test
    void findByCondition_contains_title() {
        // given
        final String[] keyword = BASE_TITLE.split(" ");
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle(keyword[0]);

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(totalBookCount);
    }

    @Test
    void findByCondition_not_contains_title() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle("notExist");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void findByCondition_contains_author_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setAuthor("kim");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(totalBookCount);
    }

    @Test
    void findByCondition_equal_publisher_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setPublisher(PUBLISHER);

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(totalBookCount);
    }

    @Test
    void findByCondition_equal_isbn_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setIsbn(ISBN);

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(totalBookCount);
    }

    @Test
    void findByCondition_allCondition_success() {
        // given
        BookSearchCond cond = new BookSearchCond();
        cond.setTitle("Book");
        cond.setPublisher(PUBLISHER);
        cond.setIsbn(ISBN);
        cond.setAuthor("kim");

        // when
        final List<Book> result = bookRepository.findBooksByCondition(cond);

        // then
        assertThat(result).hasSize(totalBookCount);
    }

    @Test
    void findByConditionWithPage() {
        // given
        int page = 0;
        int size = 10;
        BookSearchCond cond = new BookSearchCond();
        final Pageable pageable = PageRequest.of(page, size);

        int expectedTotalPage = totalBookCount / size;

        // when
        final Page<Book> result = bookRepository.findBooksByCondition(cond, pageable);

        // then
        assertThat(result.getTotalPages()).isEqualTo(expectedTotalPage);
        assertThat(result.getTotalElements()).isEqualTo(totalBookCount);
        assertThat(result.getSize()).isEqualTo(size);
    }
}
