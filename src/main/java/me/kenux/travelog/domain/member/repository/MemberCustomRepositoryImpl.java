package me.kenux.travelog.domain.member.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;
import org.springframework.util.StringUtils;

import java.util.List;

import static me.kenux.travelog.domain.member.entity.QMember.*;

@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Member> findMemberByCondition(MemberSearchCond cond) {
        return queryFactory.select(member)
            .from(member)
            .where(eqEmail(cond), eqName(cond), eqStatus(cond))
            .fetch();
    }

    private BooleanExpression eqEmail(MemberSearchCond cond) {
        if (cond == null || !StringUtils.hasText(cond.getEmail())) {
            return null;
        }
        return member.email.eq(cond.getEmail());
    }

    private BooleanExpression eqName(MemberSearchCond cond) {
        if (cond == null || !StringUtils.hasText(cond.getName())) {
            return null;
        }
        return member.name.eq(cond.getName());
    }

    private BooleanExpression eqStatus(MemberSearchCond cond) {
        if (cond == null || cond.getStatus() == null) {
            return null;
        }
        return member.status.eq(cond.getStatus());
    }
}
