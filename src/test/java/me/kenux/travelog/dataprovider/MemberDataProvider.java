package me.kenux.travelog.dataprovider;

import jakarta.persistence.EntityManager;
import lombok.Getter;
import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;

public record MemberDataProvider(EntityManager em) {

    public static final String MEMBER_NAME = "member 1";
    public static final String MEMBER_EMAIL = "member1@test.com";

    public Member provideMemberData() {
        final Member member = Member.builder()
            .name(MEMBER_NAME)
            .userRole(UserRole.USER)
            .email(MEMBER_EMAIL)
            .build();
        em.persist(member);
        return member;
    }
}
