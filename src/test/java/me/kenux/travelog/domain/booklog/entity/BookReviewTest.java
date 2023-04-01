package me.kenux.travelog.domain.booklog.entity;

import me.kenux.travelog.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class BookReviewTest {

    BookReview bookReview;

    @BeforeEach
    void setup() {
        Member member = Mockito.mock(Member.class);
        Book book = Mockito.mock(Book.class);
        String review = "review content";
        Integer rate = 5;
        bookReview = BookReview.createBookReview(book, member, review, rate);
    }

    @Test
    @DisplayName("Book is null, create BookReview failed - NullPointException")
    void createBookReview_exception() {
        // given
        Member member = Mockito.mock(Member.class);
        String review = "review content";
        int rate = 5;

        // when
        final Throwable throwable = catchThrowable(() -> BookReview.createBookReview(null, member, review, rate));

        // then
        assertThat(throwable).isInstanceOf(NullPointerException.class);
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