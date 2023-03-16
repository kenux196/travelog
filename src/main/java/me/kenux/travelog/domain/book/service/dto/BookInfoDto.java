package me.kenux.travelog.domain.book.service.dto;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.book.entity.Book;

import java.time.OffsetDateTime;
@Data
@Builder
public class BookInfoDto {
    private Long id;
    private String title;
    private String authors;
    private String contents;
    private String isbn;
    private String publisher;
    private String status;
    private String thumbnail;
    private Integer price;
    private OffsetDateTime datetime;

    public static Book toEntity(BookInfoDto bookInfo) {
        return Book.builder()
                .title(bookInfo.getTitle())
                .authors(bookInfo.getAuthors())
                .contents(bookInfo.getContents())
                .isbn(bookInfo.getIsbn())
                .price(bookInfo.getPrice())
                .publisher(bookInfo.getPublisher())
                .publishedDate(bookInfo.getDatetime().toLocalDate())
                .thumbnail(bookInfo.getThumbnail())
                .build();
    }
}