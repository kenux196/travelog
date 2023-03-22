package me.kenux.travelog.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.book.entity.BookLog;
import me.kenux.travelog.domain.book.repository.dto.BookLogSearchCond;

import java.util.List;

import static me.kenux.travelog.domain.book.entity.QBookLog.*;

@RequiredArgsConstructor
public class BookLogCustomRepositoryImpl implements BookLogCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookLog> findBySearchCond(BookLogSearchCond cond) {
        return queryFactory.select(bookLog)
                .from(bookLog)
                .where(eqMemberId(cond), eqBookId(cond), eqBookStatus(cond))
                .fetch();
    }

    private BooleanExpression eqMemberId(BookLogSearchCond cond) {
        if (cond.getMemberId() == null) {
            return null;
        }
        return bookLog.member.id.eq(cond.getMemberId());
    }

    private BooleanExpression eqBookId(BookLogSearchCond cond) {
        if (cond.getBookId() == null) {
            return null;
        }
        return bookLog.book.id.eq(cond.getBookId());
    }

    private BooleanExpression eqBookStatus(BookLogSearchCond cond) {
        if (cond.getBookStatus() == null) {
            return null;
        }
        return bookLog.bookStatus.eq(cond.getBookStatus());
    }
}