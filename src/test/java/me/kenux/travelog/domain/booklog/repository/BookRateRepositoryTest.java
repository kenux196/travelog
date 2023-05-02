package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookRate;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BookRateRepositoryTest extends RepositoryTestConfigure {

    public static final List<Integer> BOOK_RATES = Arrays.asList(1, 2, 3, 4, 5);

    @Autowired
    EntityManager em;
    @Autowired
    BookRatingRepository bookRatingRepository;

    private Book book;
    private Member member;

    @BeforeEach
    void setup() {
        book = provideBookData();
        member = provideMemberData();
        provideBookRatingData();
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
        int sumRate = 0;
        for (int rate : BOOK_RATES) {
            sumRate += rate;
        }
        final float expected = (float) sumRate / (float) BOOK_RATES.size();

        // when
        final Double avgRating = bookRatingRepository.getBookRating(book.getId());

        // then
        assertThat(avgRating).isEqualTo(expected);
    }

    private void provideBookRatingData() {
        BOOK_RATES.forEach(rate -> {
            BookRate bookRate = BookRate.createBookRate(book, member, rate);
            em.persist(bookRate);
        });
        em.flush();
        em.clear();
    }

    private Member provideMemberData() {
        final UserPassword password = new UserPassword("password");
        em.persist(password);
        final Member member = Member.builder()
            .name("member1")
            .password(password)
            .userRole(UserRole.USER)
            .email("member1@test.com")
            .build();
        em.persist(member);
        return member;
    }

    private Book provideBookData() {
        String title = "Book";
        String authors = "Authors";
        String isbn = "ISBN-123";
        String publisher = "publisher";
        LocalDate publishedDate = LocalDate.of(2023, 3, 20);
        final Book book = Book.createNewBook(title, authors, isbn, publishedDate, publisher);
        em.persist(book);
        return book;
    }
}
