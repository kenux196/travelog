package me.kenux.travelog.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "authors", nullable = false)
    private String authors;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "content", columnDefinition = "LONGTEXT")
    private String contents;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(name = "price")
    private Integer price;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "publisher")
    private String publisher;
}