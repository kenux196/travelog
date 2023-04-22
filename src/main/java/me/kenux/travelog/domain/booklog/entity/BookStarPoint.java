package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.common.BaseTimeEntity;

@Entity
@Table(name = "book_start_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookStarPoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "star_point", nullable = false)
    private Integer starPoint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BookStarPoint(Book book, int starPoint) {
        this.book = book;
        this.starPoint = starPoint;
    }

    public static BookStarPoint createStarPoint(Book book, int point) {
        return new BookStarPoint(book, point);
    }
}
