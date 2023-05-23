package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.dataprovider.BookDataProvider;
import me.kenux.travelog.dataprovider.MemberDataProvider;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookRate;
import me.kenux.travelog.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BookRateRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    EntityManager em;
    @Autowired
    BookRatingRepository bookRatingRepository;

    private Book book;
    private Member member;
    private static final List<Integer> BOOK_RATES = Arrays.asList(1, 2, 3, 4, 5);

    @BeforeEach
    void setup() {
        BookDataProvider bookDataProvider = new BookDataProvider(em);
        MemberDataProvider memberDataProvider = new MemberDataProvider(em);
        book = bookDataProvider.provideBookData();
        member = memberDataProvider.provideMemberData();
        createBookRate();
    }

    @Test
    void saveBookRate() {
        final BookRate rate = BookRate.createBookRate(book, member, 1);

        Throwable throwable = catchThrowable(() -> bookRatingRepository.save(rate));

        assertThat(throwable).isNull();
    }

    @Test
    void findStarPointByBookId() {
        // when
        List<BookRate> foundPoint = bookRatingRepository.findByBookId(book.getId());

        // then
        assertThat(foundPoint).hasSize(5);
    }

    @Test
    void getAverageBookRating() {
        // given
        int sumRate = BOOK_RATES.stream().mapToInt(i -> i).sum();
        final float expected = (float) sumRate / (float) BOOK_RATES.size();

        // when
        final Double avgRating = bookRatingRepository.getBookRating(book.getId());

        // then
        assertThat(avgRating).isEqualTo(expected);
    }

    private void createBookRate() {
        BOOK_RATES.forEach(rate -> {
            BookRate bookRate = BookRate.createBookRate(book, member, rate);
            em.persist(bookRate);
        });
    }
}
