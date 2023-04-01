package me.kenux.travelog.domain.booklog.entity;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class BookLogTest {

    @Test
    void createBookLog() {
        // given
        final Member member = getTestMember();
        final Book book = getTestBook();

        // when
        BookLog bookLog = BookLog.createNewLog(book, member);

        // then
        assertThat(bookLog.getBook().getTitle()).isEqualTo("book1");
        assertThat(bookLog.getMember().getName()).isEqualTo("user1");
        assertThat(bookLog.getBookStatus()).isEqualTo(BookStatus.NOT_STARTED);
    }

    @Test
    void whenStartRead_thenBookStatus_READ() {
        // given
        BookLog bookLog = createTestBookLog();

        // when
        bookLog.startRead();

        // then
        assertThat(bookLog.getBookStatus()).isEqualTo(BookStatus.READ);
        assertThat(bookLog.getStartDate()).isNotNull();
    }

    @Test
    void whenEndRead_thenBookStatus_DONE() {
        // given
        BookLog bookLog = createTestBookLog();

        // when
        bookLog.endRead();

        // then
        assertThat(bookLog.getBookStatus()).isEqualTo(BookStatus.DONE);
        assertThat(bookLog.getEndDate()).isNotNull();
    }

    @Test
    void whenSTOPRead_thenBookStatus_STOP() {
        // given
        BookLog bookLog = createTestBookLog();

        // when
        bookLog.stopRead();

        // then
        assertThat(bookLog.getBookStatus()).isEqualTo(BookStatus.STOP);
        assertThat(bookLog.getEndDate()).isNotNull();
    }

    @Test
    @DisplayName("읽기 종료 날짜가 있는 책은 시작일과 종료일로 기간 계산한다.")
    void getReadPeriodForReadDone() {
        // given
        BookLog bookLog = createTestBookLog();
        bookLog.startRead();
        ReflectionTestUtils.setField(bookLog, "startDate", LocalDate.of(2023, 1, 1));
        bookLog.endRead();
        ReflectionTestUtils.setField(bookLog, "endDate", LocalDate.of(2023, 1, 3));

        // when
        long days = bookLog.getReadPeriod();

        // then
        assertThat(days).isEqualTo(3);
    }

    @Test
    @DisplayName("읽기 종료 날짜가 없는 책은 시작일과 현재일로 기간 계산한다.")
    void getReadPeriodForReadNotDone() {
        // given
        BookLog bookLog = createTestBookLog();
        bookLog.startRead();
        ReflectionTestUtils.setField(bookLog, "startDate", LocalDate.of(2023, 1, 1));

        // when
        long days = bookLog.getReadPeriod();

        // then
        assertThat(days).isGreaterThan(1);
    }

    private BookLog createTestBookLog() {
        return BookLog.createNewLog(getTestBook(), getTestMember());
    }

    private Member getTestMember() {
        return Member.builder()
                .name("user1")
                .email("user1@email.com")
                .password(new UserPassword("password"))
                .userRole(UserRole.USER)
                .build();
    }

    private Book getTestBook() {
        return Book.builder()
                .title("book1")
                .authors("author1")
                .publishedDate(LocalDate.of(2023, 1, 1))
                .publisher("publisher1")
                .isbn("isbn-1")
                .build();
    }
}