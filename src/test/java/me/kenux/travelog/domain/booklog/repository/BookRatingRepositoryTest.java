package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookRating;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRatingRepositoryTest extends RepositoryTestConfigure {

    public static final List<Integer> RATINGS = Arrays.asList(1, 2, 3, 4, 5);

    @Autowired
    EntityManager em;
    @Autowired
    BookRatingRepository pointRepository;

    private Book book;

    @BeforeEach
    void setup() {
        book = provideBookData();
        provideBookRatingData();
    }

    @Test
    void findStarPointByBookId() {
        // when
        List<BookRating> foundPoint = pointRepository.findByBookId(book.getId());

        // then
        assertThat(foundPoint).hasSize(5);
    }

    @Test
    void getAverageBookRating() {
        final Double avgRating = pointRepository.getBookRating(book.getId());

        int sumRating = 0;
        for (int rating : RATINGS) {
            sumRating += rating;
        }
        final float providedAvgRating = (float) sumRating / (float) RATINGS.size();

        assertThat(avgRating).isEqualTo(providedAvgRating);
    }

    private void provideBookRatingData() {
        RATINGS.forEach(rating -> {
            BookRating bookRating = BookRating.createBookRating(book, rating);
            em.persist(bookRating);
        });
        em.flush();
        em.clear();
    }

    private Book provideBookData() {
        String title = "Book";
        String authors= "Authors";
        String isbn = "ISBN-123";
        String publisher = "publisher";
        LocalDate publishedDate = LocalDate.of(2023, 3, 20);
        final Book book = Book.createNewBook(title, authors, isbn, publishedDate, publisher);
        em.persist(book);
        return book;
    }
}
