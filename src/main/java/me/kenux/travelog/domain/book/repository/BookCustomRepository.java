package me.kenux.travelog.domain.book.repository;

import me.kenux.travelog.domain.book.entity.Book;
import me.kenux.travelog.domain.book.repository.dto.BookSearchCond;

import java.util.List;

public interface BookCustomRepository {

    List<Book> findBooksByCondition(BookSearchCond cond);
}
