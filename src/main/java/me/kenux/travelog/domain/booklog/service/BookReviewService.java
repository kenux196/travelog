package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.BookReviewRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewResponse;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSaveRequest;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSearchCond;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookReviewService {

    private final BookReviewRepository bookReviewRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public void saveReview(BookReviewSaveRequest request) {
        final Member member = getMember();
        final Book book = getBook(request.getBookId());

        final BookReview bookReview = BookReview
                .createBookReview(book, member, request.getReviewContents(), request.getRate());

        bookReviewRepository.save(bookReview);
    }

    public List<BookReviewResponse> getBookReviews(BookReviewSearchCond cond) {
        return null;
//        return bookReviewRepository.findBySearchCond(cond);
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }

    private Member getMember() {
        final String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberRepository.findByEmail(userName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }
}
