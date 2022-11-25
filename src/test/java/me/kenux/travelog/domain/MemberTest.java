package me.kenux.travelog.domain;

import me.kenux.travelog.domain.enums.MemberStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
    @DisplayName("탈퇴한 멤버의 상태는 LEAVED")
    void leaveMemberTest() {
        // given
        final Member member = getMember();

        // when
        member.leave();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.LEAVED);
    }

    @Test
    @DisplayName("불량 회원의 상태는 BLOCKED")
    void blockedMemberTest() {
        // given
        final Member member = getMember();

        // when
        member.block();

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.BLOCKED);
    }

    private static Member getMember() {
        return Member.createNewMember("member1", "member1@email.com");
    }

}
