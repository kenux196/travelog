package me.kenux.travelog.domain.booklog.repository.dto;

import lombok.Data;

@Data
public class BookSearchCond {

    private String title;
    private String author;
    private String publisher;
    private String isbn;
}