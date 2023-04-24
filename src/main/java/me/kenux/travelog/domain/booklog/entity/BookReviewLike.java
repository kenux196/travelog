package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.member.entity.Member;

@Entity
@Table(name = "book_review_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookReviewLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_review_id", nullable = false)
    private BookReview bookReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    // TODO - create ddl 2023-04-24 sky
}
