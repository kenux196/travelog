package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSearchCond;

import java.util.List;

public interface BookReviewCustomRepository {

    List<BookReview> findReviewBy(BookReviewSearchCond cond);

    List<BookReview> findReviewWithMemberBy(Long bookId);
}
