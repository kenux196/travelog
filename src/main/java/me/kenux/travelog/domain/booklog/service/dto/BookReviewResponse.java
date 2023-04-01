package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.booklog.entity.BookReview;

import java.time.OffsetDateTime;

@Data
@Builder
public class BookReviewResponse {
    private String writer;
    private String review;
    private Integer rate;
    private OffsetDateTime datetime;

    public static BookReviewResponse from(BookReview bookReview) {
        return BookReviewResponse.builder()
                .writer(bookReview.getMember().getName())
                .review(bookReview.getReview())
                .rate(bookReview.getRate())
                .datetime(bookReview.getCreatedDate())
                .build();
    }
}
