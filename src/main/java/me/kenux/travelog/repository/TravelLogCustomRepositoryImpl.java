package me.kenux.travelog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.TravelLog;
import me.kenux.travelog.repository.cond.TravelLogSearchCond;

import java.util.List;

import static me.kenux.travelog.domain.QTravelLog.travelLog;

@RequiredArgsConstructor
public class TravelLogCustomRepositoryImpl implements TravelLogCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<TravelLog> findAllByCondition(TravelLogSearchCond cond) {
        return queryFactory.selectFrom(travelLog)
            .where(eqMemberId(cond))
            .orderBy(travelLog.id.desc())
            .fetch();
    }

    private BooleanExpression eqMemberId(TravelLogSearchCond cond) {
        if (cond.getMemberId() == null) {
            return null;
        }
        return travelLog.member.id.eq(cond.getMemberId());
    }

}
