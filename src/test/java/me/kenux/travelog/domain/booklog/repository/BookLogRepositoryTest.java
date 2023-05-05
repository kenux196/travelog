package me.kenux.travelog.domain.booklog.repository;

import jakarta.persistence.EntityManager;
import me.kenux.travelog.RepositoryTestConfigure;
import me.kenux.travelog.dataprovider.BookDataProvider;
import me.kenux.travelog.dataprovider.MemberDataProvider;
import me.kenux.travelog.domain.booklog.entity.Book;
import me.kenux.travelog.domain.booklog.entity.BookLog;
import me.kenux.travelog.domain.booklog.entity.BookStatus;
import me.kenux.travelog.domain.booklog.repository.dto.BookLogSearchCond;
import me.kenux.travelog.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookLogRepositoryTest extends RepositoryTestConfigure {

    @Autowired
    EntityManager em;
    @Autowired
    BookLogRepository bookLogRepository;

    private Member member;
    private Book book;
    private BookLog bookLog;

    @BeforeEach
    void setup() {
        BookDataProvider bookDataProvider = new BookDataProvider(em);
        book = bookDataProvider.provideBookData();
        MemberDataProvider memberDataProvider = new MemberDataProvider(em);
        member = memberDataProvider.provideMemberData();
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
