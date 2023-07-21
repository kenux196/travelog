package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.dto.BookSearchCond;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookCustomRepository {

    List<Book> findBooksByCondition(BookSearchCond cond);

    Page<Book> findBooksByCondition(BookSearchCond cond, Pageable pageable);
}
