package me.kenux.travelog.domain.book.repository;

import me.kenux.travelog.domain.book.entity.BookLog;
import me.kenux.travelog.domain.book.repository.dto.BookLogSearchCond;

import java.util.List;

public interface BookLogCustomRepository {

    List<BookLog> findBySearchCond(BookLogSearchCond cond);
}
