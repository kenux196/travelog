package me.kenux.travelog.domain.starpoint.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.common.BaseTimeEntity;

@Entity
@Table(name = "book_start_point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookStarPoint extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer point;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", insertable = false, updatable = false)
    private Book book;

    public BookStarPoint(Book book, int point) {
        this.book = book;
        this.point = point;
    }

    public static BookStarPoint createStarPoint(Book book, int point) {
        return new BookStarPoint(book, point);
    }
}
