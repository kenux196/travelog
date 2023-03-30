package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;

@Data
public class BookReviewSaveRequest {

    private Long bookId;
    private String reviewContents;
    private Integer rate;
}
