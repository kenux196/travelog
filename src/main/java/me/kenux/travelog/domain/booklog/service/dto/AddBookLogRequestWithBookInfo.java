package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;

@Data
public class AddBookLogRequestWithBookInfo {

    private AddBookRequest.BookInfo bookInfo;
}
