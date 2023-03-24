package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequest;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookLogServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookLogRepository bookLogRepository;
    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private BookLogService bookLogService;

    private static SecurityContext securityContext;
    private static Authentication authentication;

    @BeforeAll
    static void setup() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = getMockSecurityContext();

    }
    private static SecurityContext getMockSecurityContext() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        return securityContext;
    }

    @Test
    @DisplayName("회원의 북로그 정보를 가져온다. - 성공")
    void getBookLogForMember() {
        // given
        given(bookLogRepository.findByMember(any())).willReturn(getDummyBookLogs());

        // when
        final List<BookLogResponse> bookLogResponses = bookLogService.getBookLogs(any());

        // then
        assertThat(bookLogResponses).isNotEmpty();
    }

    private List<BookLog> getDummyBookLogs() {
        final Book mockBook = Mockito.mock(Book.class);
        final Member mockMember = Mockito.mock(Member.class);
        return Collections.singletonList(BookLog.createNewLog(mockBook, mockMember));
    }

    @Test
    @DisplayName("새로운 북로그를 추가 실패 - 책이 존재하지 않음")
    void addBookLog_failed_bookNotFounded() {
        // given
        AddBookLogRequest request = new AddBookLogRequest();
        request.setBookId(1L);
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        // when
        final Throwable throwable = catchThrowable(() -> bookLogService.addNewBookLog(request));

        // then
        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("새로운 북로그를 추가 실패 - 회원이 존재하지 않음")
    void addBookLog_failed_memberNotFounded() {
        // given
        AddBookLogRequest request = new AddBookLogRequest();
        request.setBookId(1L);
        final Book mockBook = Mockito.mock(Book.class);
        given(bookRepository.findById(any())).willReturn(Optional.of(mockBook));
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when
        final Throwable throwable = catchThrowable(() -> bookLogService.addNewBookLog(request));

        // then
        assertThat(throwable)
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("새로운 북로그를 추가 성공")
    void addBookLog_failed_success() {
        // given
        AddBookLogRequest request = new AddBookLogRequest();
        request.setBookId(1L);
        final Book book = Book.createNewBook("book1", "author", "isbn", LocalDate.now(), "publisher");
        given(bookRepository.findById(any())).willReturn(Optional.of(book));
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(Mockito.mock(Member.class)));

        // when
        final String bookTitle = bookLogService.addNewBookLog(request);

        // then
        assertThat(bookTitle).isEqualTo("book1");
    }
}