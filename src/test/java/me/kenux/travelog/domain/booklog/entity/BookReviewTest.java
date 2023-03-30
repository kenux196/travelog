package me.kenux.travelog.domain.booklog.entity;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.QMember;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.setAllowComparingPrivateFields;
import static org.junit.jupiter.api.Assertions.*;

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