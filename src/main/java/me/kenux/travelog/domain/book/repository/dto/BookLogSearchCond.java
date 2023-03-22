package me.kenux.travelog.domain.book.repository.dto;

import lombok.Data;
import me.kenux.travelog.domain.book.entity.BookStatus;

@Data
public class BookLogSearchCond {

    private Long memberId;
    private Long bookId;
    private BookStatus bookStatus;
}