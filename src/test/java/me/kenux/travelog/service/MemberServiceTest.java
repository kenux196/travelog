package me.kenux.travelog.service;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.domain.enums.MemberStatus;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import me.kenux.travelog.service.dto.request.MemberJoinRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        // when
        memberService.joinMember(request);

        // then
//        verify(memberRepository, times(1)).save(any());
//        verify(passwordRepository, times(1)).save(any());
        then(memberRepository).should(times(1)).save(any());
        then(passwordRepository).should(times(1)).save(any());
    }

    @Test
    @DisplayName("회원 탈퇴 처리가 되면 회원의 상태는 LEAVED이다.")
    void leave_member_test() {
        // given
        Member member = new Member("member1", "member1@email.com");
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        // when
        memberService.leaveMember(any());

        // then
        assertThat(member.getStatus()).isEqualTo(MemberStatus.LEAVED);
    }
}
