package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.*;
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

    @Column(name = "rate", nullable = false)
    private Integer rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Builder
    public BookReview(@NonNull Book book, @NonNull Member member, @NonNull String review, @NonNull Integer rate) {
        this.book = book;
        this.member = member;
        this.review = review;
        this.rate = rate;
    }

    public static BookReview createBookReview(Book book, Member member, String review, Integer rate) {
        return BookReview.builder()
                .book(book)
                .member(member)
                .review(review)
                .rate(rate)
                .build();
    }

    public void updateReview(String review) {
        this.review = review;
    }

    public void updateRate(Integer rate) {
        this.rate = rate;
    }
}
