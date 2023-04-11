package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequest;
import me.kenux.travelog.domain.booklog.service.dto.AddBookLogRequestWithBookInfo;
import me.kenux.travelog.domain.booklog.service.dto.BookInfoDto;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.mock;

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

    private Member mockMember;
    private Book mockBook;

    private static SecurityContext securityContext;
    private static Authentication authentication;

    @BeforeAll
    static void setupAll() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = getMockSecurityContext();

    }
    private static SecurityContext getMockSecurityContext() {
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        return securityContext;
    }

    @BeforeEach
    void setup() {
        mockMember = mock(Member.class);
        mockBook = mock(Book.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
    }

    @Test
    @DisplayName("회원의 북로그 정보를 가져온다. - 성공")
    void getBookLogForMember() {
        // given
        given(bookLogRepository.findByMember(any()))
                .willReturn(Collections.singletonList(BookLog.createNewLog(mockBook, mockMember)));

        // when
        final List<BookLogResponse> bookLogResponses = bookLogService.getBookLogs(any());

        // then
        assertThat(bookLogResponses).isNotEmpty();
    }

    @Test
    @DisplayName("새로운 북로그를 추가 실패 - 책이 존재하지 않음")
    void addBookLog_failed_bookNotFounded() {
        // given
        AddBookLogRequest request = getAddBookLogRequest();
        given(bookRepository.findAllById(any())).willReturn(new ArrayList<>());
        setStubMember();

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
        AddBookLogRequest request = getAddBookLogRequest();
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
        AddBookLogRequest request = getAddBookLogRequest();
        given(bookRepository.findAllById(any())).willReturn(Collections.singletonList(mockBook));
        setStubMember();

        // when
        final int addedBookCount = bookLogService.addNewBookLog(request);

        // then
        assertThat(addedBookCount).isEqualTo(1);
    }

    @Test
    @DisplayName("책정보를 기준으로 새로운 북로그 생성 - 기존에 등록된 책이면 바로 북로그 생성 - 성공")
    void addBookLog_withBookInfo_success() {
        // given
        AddBookLogRequestWithBookInfo request = Mockito.mock(AddBookLogRequestWithBookInfo.class);
        BookInfoDto bookInfoDto = mock(BookInfoDto.class);
        given(request.getBookInfo()).willReturn(bookInfoDto);
        given(bookRepository.findBookByIsbn(any())).willReturn(Optional.of(mockBook));
        setStubMember();

        // when
        final Long result = bookLogService.addNewBookLogWithBookInfo(request);

        // then
        then(bookLogRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("책정보를 기준으로 새로운 북로그 생성 - 기존에 등록되지 않은 책은 새로 책 등록 후 북로그 생성 성공")
    void addBookLog_withBookInfo_notExistBook_success() {
        // given
        AddBookLogRequestWithBookInfo request = Mockito.mock(AddBookLogRequestWithBookInfo.class);
        BookInfoDto bookInfoDto = BookInfoDto.builder()
                .title("title")
                .publisher("publisher")
                .authors("author")
                .isbn("isbn")
                .datetime(OffsetDateTime.now())
                .build();
        given(request.getBookInfo()).willReturn(bookInfoDto);
        given(bookRepository.findBookByIsbn(any())).willReturn(Optional.empty());
        given(bookRepository.save(any())).willReturn(mockBook);
        setStubMember();

        // when
        final Long result = bookLogService.addNewBookLogWithBookInfo(request);

        // then
        then(bookRepository).should(times(1)).save(any());
        then(bookLogRepository).should(times(1)).save(any());
    }

    private AddBookLogRequest getAddBookLogRequest() {
        AddBookLogRequest request = new AddBookLogRequest();
        List<Long> bookIds = Collections.singletonList(1L);
        request.setBookIds(bookIds);
        return request;
    }

    private void setStubMember() {
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(mockMember));
    }
}