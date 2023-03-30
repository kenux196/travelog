package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.repository.BookReviewRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSaveRequest;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

}