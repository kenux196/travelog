package me.kenux.travelog.domain.booklog.service;

import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.repository.BookLogRepository;
import me.kenux.travelog.domain.booklog.repository.BookRepository;
import me.kenux.travelog.domain.booklog.service.dto.BookLogResponse;
import me.kenux.travelog.domain.member.entity.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class BookLogServiceTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookLogRepository bookLogRepository;

    @InjectMocks
    private BookLogService bookLogService;

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
}