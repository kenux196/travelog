package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.dto.BookLogSearchCond;

import java.util.List;

public interface BookLogCustomRepository {

    List<BookLog> findBySearchCond(BookLogSearchCond cond);
}
