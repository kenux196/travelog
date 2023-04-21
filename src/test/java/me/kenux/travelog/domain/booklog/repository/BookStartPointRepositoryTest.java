package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookStarPoint;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookStartPointRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    EntityManager em;
    @Autowired
    BookStartPointRepository pointRepository;

    private List<Book> bookList;

    @BeforeEach
    void setup() {
        createBookTestData();
        bookList = em.createQuery("select b from Book b", Book.class)
            .getResultList();
    }

    @Test
    void findStarPointByBookId() {
        // given
        int point = 10;
        Book book = bookList.get(0);
        final BookStarPoint starPoint = BookStarPoint.createStarPoint(book, point);
        pointRepository.save(starPoint);
        em.flush();

        // when
        List<BookStarPoint> foundPoint = pointRepository.findByBookId(book.getId());

        // then
        assertThat(foundPoint).isNotEmpty().contains(starPoint);
    }

    @Test
    void findBook() {
        assertThat(bookList).hasSize(10);
    }

    private void createBookTestData() {
        String title = "Book";
        String author= "Author";
        String isbn = "ISBN-123";
        String publisher = "publisher";
        LocalDate publishedDate = LocalDate.of(2023, 3, 20);
        for (int i = 0; i < 10; i++) {
            final Book book = Book.createNewBook(
                title + i, author + i, isbn + i, publishedDate.plusDays(i), publisher);
            em.persist(book);
        }
        em.flush();
        em.clear();
    }
}
