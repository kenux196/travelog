package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.BookReviewRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewResponse;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSaveRequest;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class BookReviewServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    BookRepository bookRepository;
    @Mock
    BookReviewRepository bookReviewRepository;
    @InjectMocks
    BookReviewService bookReviewService;

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
    @DisplayName("책 리뷰 저장 - 정상 동작")
    void saveBookReview_success() {
        // given
        BookReviewSaveRequest request = new BookReviewSaveRequest();
        request.setBookId(1L);
        request.setReviewContents("contents");
        request.setRate(1);

        given(securityContext.getAuthentication()).willReturn(authentication);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(mock(Member.class)));
        given(bookRepository.findById(any())).willReturn(Optional.of(mock(Book.class)));

        // when
        bookReviewService.saveReview(request);

        // then
        then(bookReviewRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("책 리뷰 저장 실패 - 검색된 책이 없음")
    void saveBookReview_failed_notExistBook() {
        // given
        BookReviewSaveRequest mockRequest = mock(BookReviewSaveRequest.class);
        given(mockRequest.getBookId()).willReturn(1L);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(mock(Member.class)));
        given(bookRepository.findById(any())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> bookReviewService.saveReview(mockRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.BOOK_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("책 리뷰 저장 실패 - 회원 정보 오류")
    void saveBookReview_failed_notExistMember() {
        // given
        BookReviewSaveRequest mockRequest = mock(BookReviewSaveRequest.class);
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> bookReviewService.saveReview(mockRequest))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.MEMBER_NOT_EXIST.getMessage());
    }

    @Test
    @DisplayName("bookId로 리뷰를 조회하면, 작성자가 포함되어야 한다.")
    void getBookReviewWithMember() {
        // given
        final BookReview mockBookReview = Mockito.mock(BookReview.class);
        final Member mockMember = mock(Member.class);
        given(bookReviewRepository.findReviewWithMemberBy(any())).willReturn(Collections.singletonList(mockBookReview));
        given(mockBookReview.getMember()).willReturn(mockMember);
        given(mockMember.getName()).willReturn("writer");

        // when
        final List<BookReviewResponse> response = bookReviewService.getBookReviewWithMember(any());

        // then
        assertThat(response).hasSize(1);
        assertThat(response.get(0).getWriter()).isEqualTo("writer");
    }
}
