package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.common.BaseTimeEntity;
import me.kenux.travelog.domain.member.entity.Member;

@Entity
@Table(name = "book_rate")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookRate extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate", nullable = false)
    private Short rate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public BookRate(Book book, Member member, short rate) {
        this.book = book;
        this.member = member;
        this.rate = rate;
    }

    public static BookRate createBookRate(Book book, Member member, short point) {
        return new BookRate(book, member, point);
    }
}
