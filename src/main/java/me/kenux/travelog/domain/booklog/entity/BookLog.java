package me.kenux.travelog.domain.booklog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import me.kenux.travelog.domain.common.BaseTimeEntity;
import me.kenux.travelog.domain.member.entity.Member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static me.kenux.travelog.domain.booklog.entity.BookStatus.*;

@Entity
@Table(name = "book_log")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class BookLog extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public BookLog(@NonNull Book book, @NonNull Member member) {
        this.book = book;
        this.member = member;
    }

    public static BookLog createNewLog(Book book, Member member) {
        final BookLog bookLog = new BookLog(book, member);
        bookLog.changeBookStatus(NOT_STARTED);
        return bookLog;
    }

    public void startRead() {
        changeBookStatus(READ);
        this.startDate = LocalDate.now();
    }

    public void endRead() {
        changeBookStatus(DONE);
        setEndDate();
    }

    public void stopRead() {
        changeBookStatus(STOP);
        setEndDate();
    }

    private void changeBookStatus(BookStatus status) {
        this.bookStatus = status;
    }

    private void setEndDate() {
        this.endDate = LocalDate.now();
    }

    public long getReadPeriod() {
        LocalDate end = endDate != null ? endDate : LocalDate.now();
        return calculateDays(startDate, end) + 1;
    }

    private long calculateDays(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }
}