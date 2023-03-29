package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.common.BaseTimeEntity;
import me.kenux.travelog.domain.member.entity.Member;

@Entity
@Table(name = "book_review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookReview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review", length = 1000, nullable = false)
    private String review;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public BookReview(Book book, Member member, String review, Integer rating) {
        this.book = book;
        this.member = member;
        this.review = review;
        this.rating = rating;
    }

    public static BookReview createBookReview(Book book, Member member, String review, Integer rating) {
        return BookReview.builder()
                .book(book)
                .member(member)
                .review(review)
                .rating(rating)
                .build();
    }

    public void updateReview(String review) {
        this.review = review;
    }

    public void updateRating(Integer rating) {
        this.rating = rating;
    }
}
