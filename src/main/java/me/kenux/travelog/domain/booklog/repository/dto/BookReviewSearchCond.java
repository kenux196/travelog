package me.kenux.travelog.domain.booklog.repository.dto;

import lombok.Data;

@Data
public class BookReviewSearchCond {
    private Long memberId;
    private Long bookId;
}
