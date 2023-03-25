package me.kenux.travelog.domain.booklog.service;

import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequest;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberCustomRepository;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.service.dto.UserDetailsImpl;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static me.kenux.travelog.domain.booklog.entity.QBook.book;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookLogService {

    private final BookRepository bookRepository;
    private final BookLogRepository bookLogRepository;
    private final MemberRepository memberRepository;

    public List<BookLogResponse> getBookLogs(Long memberId) {
        final List<BookLog> bookLogs = bookLogRepository.findByMember(memberId);
        return bookLogs.stream()
                .map(BookLogResponse::from)
                .toList();
    }

    @Transactional
    public int addNewBookLog(AddBookLogRequest request) {
        final Member member = getMember();
        final List<BookLog> newBookLogs = getBooks(request.getBookIds()).stream()
                .map(book -> BookLog.createNewLog(book, member))
                .toList();
        bookLogRepository.saveAll(newBookLogs);
        return newBookLogs.size();
    }

    private Member getMember() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return memberRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_EXIST));
    }

    private List<Book> getBooks(List<Long> bookIds) {
        final List<Book> books = bookRepository.findAllById(bookIds);
        if (books.isEmpty()) {
            throw new CustomException(ErrorCode.BOOK_NOT_FOUND);
        }
        return books;
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new CustomException(ErrorCode.BOOK_NOT_FOUND));
    }
}