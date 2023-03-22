package me.kenux.travelog.domain.booklog.repository;

import me.kenux.travelog.BaseRepositoryConfig;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.entity.BookStatus;
import me.kenux.travelog.domain.booklog.repository.dto.BookLogSearchCond;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookLogRepositoryTest extends BaseRepositoryConfig {

    @Autowired
    PasswordRepository passwordRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookLogRepository bookLogRepository;

    private Member member;
    private Book book;
    private BookLog bookLog;

    @BeforeEach
    void setup() {
        final UserPassword password = new UserPassword("1");
        passwordRepository.save(password);
        member = Member.createNewMember("member1", "member1@test.com", password);
        memberRepository.save(member);
        LocalDate publishDate = LocalDate.of(2023, 1, 1);
        book = Book.createNewBook("book1", "author1", "isbn", publishDate, "publisher");
        bookRepository.save(book);

        bookLog = BookLog.createNewLog(book, member);
        bookLogRepository.save(bookLog);
    }

    @Test
    void findBookLogByMember() {
        // when
        List<BookLog> bookLogs = bookLogRepository.findByMember(member.getId());

        // then
        assertThat(bookLogs).hasSize(1);
    }

    @Test
    void findBookLogByBook() {
        // when
        List<BookLog> bookLogs = bookLogRepository.findByBook(book.getId());

        // then
        assertThat(bookLogs).hasSize(1);
    }

    @Test
    void findBookLogBySearchCond_eqMemberId() {
        // given
        BookLogSearchCond cond = new BookLogSearchCond();
        cond.setMemberId(member.getId());

        // when
        final List<BookLog> bookLogs = bookLogRepository.findBySearchCond(cond);

        // then
        assertThat(bookLogs).hasSize(1).contains(bookLog);
    }

    @Test
    void findBookLogBySearchCond_eqBookId() {
        // given
        BookLogSearchCond cond = new BookLogSearchCond();
        cond.setBookId(book.getId());

        // when
        final List<BookLog> bookLogs = bookLogRepository.findBySearchCond(cond);

        // then
        assertThat(bookLogs).hasSize(1).contains(bookLog);
    }

    @Test
    void findBookLogBySearchCond_eqBookStatus() {
        // given
        BookLogSearchCond cond = new BookLogSearchCond();
        cond.setBookStatus(BookStatus.NOT_STARTED);

        // when
        final List<BookLog> bookLogs = bookLogRepository.findBySearchCond(cond);

        // then
        assertThat(bookLogs).hasSize(1).contains(bookLog);
    }
}