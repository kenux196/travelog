package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.*;
import me.kenux.travelog.domain.common.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Book extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "isbn", nullable = false)
    private String isbn;

    @Column(name = "contents", length = 1000)
    private String contents;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Column(name = "publisher", nullable = false)
    private String publisher;

    @Column(name = "price")
    private Integer price;

    @Column(name = "rate")
    private Float rate;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Builder
    public Book(@NonNull String title, @NonNull String authors, @NonNull String isbn,
                @NonNull LocalDate publishedDate, @NonNull String publisher) {
        this.title = title;
        this.authors = authors;
        this.isbn = isbn;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

    public static Book createNewBook(String title,
                                     String authors,
                                     String isbn,
                                     LocalDate publishedDate,
                                     String publisher) {
        return new Book(title, authors, isbn, publishedDate, publisher);
    }

    public boolean isSameBook(String title, String isbn) {
        return this.title.equals(title) && this.isbn.equals(isbn);
    }

    public void updateContents(String contents) {
        this.contents = contents;
    }

    public void updateThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}