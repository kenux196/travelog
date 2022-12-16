package me.kenux.travelog.domain.travellog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.travellog.entity.QTravelLog;
import me.kenux.travelog.domain.travellog.entity.TravelLog;
import me.kenux.travelog.domain.travellog.repository.dto.TravelLogSearchCond;

import java.util.List;

import static me.kenux.travelog.domain.travellog.entity.QTravelLog.travelLog;

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
        return travelLog.memberId.eq(cond.getMemberId());
    }

}
