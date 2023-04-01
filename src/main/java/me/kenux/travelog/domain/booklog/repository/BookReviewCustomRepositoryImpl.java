package me.kenux.travelog.domain.booklog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.booklog.entity.BookReview;
import me.kenux.travelog.domain.booklog.service.dto.BookReviewSearchCond;

import java.util.List;

import static me.kenux.travelog.domain.booklog.entity.QBookReview.bookReview;

@RequiredArgsConstructor
public class BookReviewCustomRepositoryImpl implements BookReviewCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<BookReview> findReviewByCondition(BookReviewSearchCond cond) {
        return queryFactory
                .select(bookReview)
                .from(bookReview)
                .where(eqBook(cond.getBookId()), eqMember(cond.getMemberId()))
                .fetch();
    }

    @Override
    public List<BookReview> findReviewWithMemberBy(Long bookId) {
        return queryFactory
                .select(bookReview).from(bookReview)
                .join(bookReview.member).fetchJoin()
                .where(eqBook(bookId))
                .fetch();
    }

    private BooleanExpression eqBook(Long bookId) {
        if (bookId == null) {
            return null;
        }
        return bookReview.book.id.eq(bookId);
    }

    private BooleanExpression eqMember(Long memberId) {
        if (memberId == null) {
            return null;
        }
        return bookReview.member.id.eq(memberId);
    }

}
