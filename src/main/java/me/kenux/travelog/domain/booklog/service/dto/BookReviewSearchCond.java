package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Data;

@Data
public class BookReviewSearchCond {
    private Long memberId;
    private Long bookId;
}
