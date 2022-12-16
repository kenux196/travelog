package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;
import me.kenux.travelog.domain.member.service.MemberService;
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
    @Mock
    PasswordEncoder passwordEncoder;
    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("회원 중복인 경우 예외 발생해야 한다.")
    void duplicated_member_test() {
        // given
        MemberJoinRequest request = new MemberJoinRequest();
        request.setName("testUser1");
        request.setEmail("testUser1@email.com");
        request.setPassword("password");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        assertThatThrownBy(() -> memberService.joinMember(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.EMAIL_DUPLICATION.getMessage());
    }

    @Test
    @DisplayName("회원 가입 성공")
    void save_password_success_join_member() {
        // given
        MemberJoinRequest request = new MemberJoinRequest();
        request.setName("testUser1");
        request.setEmail("testUser1@email.com");
        request.setPassword("password");
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(passwordEncoder.encode(any())).willReturn(any());

        // when
        memberService.joinMember(request);

        // then
//        verify(memberRepository, times(1)).save(any());
//        verify(passwordRepository, times(1)).save(any());
        then(passwordEncoder).should(times(1)).encode(any());
        then(memberRepository).should(times(1)).save(any());
        then(passwordRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("회원 탈퇴 처리가 되면 회원의 상태는 LEAVED이다.")
    void leave_member_test() {
        // given
        Member member = Member.builder()
            .name("member1")
            .email("member1@email.com")
            .build();
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.leaveMember(any());

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.LEAVED);
    }
}
