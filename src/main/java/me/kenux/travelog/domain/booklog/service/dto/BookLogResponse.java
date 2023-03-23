package me.kenux.travelog.domain.booklog.service.dto;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.entity.BookStatus;

import java.time.LocalDate;

@Data
@Builder
public class BookLogResponse {

    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BookStatus bookStatus;
    private BookInfoDto.SimpleInfo bookInfo;

    public static BookLogResponse from(BookLog bookLog) {
        return BookLogResponse.builder()
                .id(bookLog.getId())
                .bookStatus(bookLog.getBookStatus())
                .startDate(bookLog.getStartDate())
                .endDate(bookLog.getEndDate())
                .bookInfo(BookInfoDto.SimpleInfo.from(bookLog.getBook()))
                .build();
    }
}