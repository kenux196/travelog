package me.kenux.travelog.domain.booklog.entity;

import me.kenux.travelog.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;

class BookReviewTest {

    BookReview bookReview;

    @BeforeEach
    void setup() {
        Member member = mock(Member.class);
        Book book = mock(Book.class);
        String review = "review content";
        Integer rate = 5;
        bookReview = BookReview.createBookReview(book, member, review, rate);
    }

    @Test
    @DisplayName("If param contains null, then create BookReview failed - NPE")
    void createBookReview_exception() {
        // given
        Book book = mock(Book.class);
        Member member = mock(Member.class);
        String review = "review content";
        int rate = 5;

        // when
        assertThatThrownBy(() -> BookReview.createBookReview(null, member, review, rate))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> BookReview.createBookReview(book, null, review, rate))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> BookReview.createBookReview(book, member, null, rate))
                .isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> BookReview.createBookReview(book, member, review, null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void updateBookReview_success() {
        // given
        String newReviewContents = "new review Content";

        // when
        bookReview.updateReview(newReviewContents);

        // then
        assertThat(bookReview.getReview()).isEqualTo(newReviewContents);
    }

    @Test
    void updateRatingPoint() {
        // given
        Integer rate = 10;

        // when
        bookReview.updateRate(rate);

        // then
        assertThat(bookReview.getRate()).isEqualTo(rate);
    }

}
