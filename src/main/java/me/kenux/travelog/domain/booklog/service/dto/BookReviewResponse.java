package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class BookReviewResponse {
    private String writer;
    private String review;
    private Integer rate;
    private OffsetDateTime datetime;
}
