package me.kenux.travelog.web.api.book;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
@Data
@Builder
public class BookInfoBase {
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
}