package me.kenux.travelog.domain.member.repository;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.dto.MemberSearchCond;

import java.util.List;

public interface MemberCustomRepository {

    List<Member> findMemberByCondition(MemberSearchCond cond);
}
