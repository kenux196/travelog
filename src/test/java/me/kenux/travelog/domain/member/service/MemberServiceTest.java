package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.member.dto.request.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordRepository passwordRepository;

    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원이 탈퇴하면 회원의 개인정보는 삭제되어야 한다.")
    void leave_member_test() {
        // given
        Member member = Member.builder()
            .name("member1")
            .email("member1@email.com")
            .build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.removeMember(any());

        // then
        then(memberRepository).should(times(1)).delete(any());
        then(passwordRepository).should(times(1)).delete(any());
    }

    @Test
    @DisplayName("회원상태 정보를 블럭 상태로 변경한다.")
    void change_member_block() {
        // given
        Member member = Member.builder()
            .name("member1")
            .email("member1@email.com")
            .build();
        ReflectionTestUtils.setField(member, "id", 1L);
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.blockMember(1L);

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.BLOCKED);
    }
}
