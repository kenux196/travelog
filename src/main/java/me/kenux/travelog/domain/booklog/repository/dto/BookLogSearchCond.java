package me.kenux.travelog.domain.booklog.repository.dto;

import lombok.Data;
import me.kenux.travelog.domain.booklog.entity.BookStatus;

@Data
public class BookLogSearchCond {

    private Long memberId;
    private Long bookId;
    private BookStatus bookStatus;
}