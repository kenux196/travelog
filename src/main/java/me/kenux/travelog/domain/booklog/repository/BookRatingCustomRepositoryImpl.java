package me.kenux.travelog.domain.booklog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import static me.kenux.travelog.domain.booklog.entity.QBookRate.bookRate;

@RequiredArgsConstructor
public class BookRatingCustomRepositoryImpl implements BookRatingCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Double getBookRating(Long bookId) {
        return queryFactory.select(bookRate.rate.avg())
            .from(bookRate)
            .where(bookRate.book.id.eq(bookId))
            .fetchOne();
    }
}
