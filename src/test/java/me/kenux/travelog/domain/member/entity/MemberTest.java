package me.kenux.travelog.domain.member.entity;

import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("신규 멤버의 상태는 NORMAL")
    void createNewMemberTest() {
        // given
        final Member newMember = getMember();

        // when then
        assertThat(newMember.getStatus()).isEqualTo(MemberStatus.NORMAL);
    }

    @Test
    @DisplayName("불량 회원의 상태는 BLOCKED")
    void blockedMemberTest() {
        // given
        final Member member = getMember();

        // when
        member.doBlock();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.BLOCKED);
    }

    @Test
    @DisplayName("회원의 마지막 접속일 갱신")
    void updateLastAccessDate() {
        // given
        final Member member = getMember();
        OffsetDateTime before = OffsetDateTime.of(
            2022, 10, 10, 10, 10, 10, 00, ZoneOffset.UTC);
        ReflectionTestUtils.setField(member, "lastAccessDate", before);

        // when
        member.successLogin();

        // then
        assertThat(member.getLastAccessDate()).isNotEqualTo(before);
    }

    @Test
    @DisplayName("회원의 비밀번호는 UserPassword에서 읽어온다.")
    void getPasswordTest() {
        final UserPassword userPassword = new UserPassword("password");
        final Member member = Member.createNewMember("member1", "member1@email.com", userPassword);

        final String memberPassword = member.getPassword();

        assertThat(memberPassword).isEqualTo(userPassword.getPassword());
    }

    private static Member getMember() {
        final UserPassword password = new UserPassword("password");
        return Member.createNewMember("member1", "member1@email.com", password);
    }

}
