package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_rating")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rating", nullable = false)
    private Integer rating;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public BookRating(Book book, int rating) {
        this.book = book;
        this.rating = rating;
    }

    public static BookRating createStarPoint(Book book, int point) {
        return new BookRating(book, point);
    }
}
