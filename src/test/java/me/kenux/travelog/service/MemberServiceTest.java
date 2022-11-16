package me.kenux.travelog.service;

import me.kenux.travelog.domain.Member;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.repository.MemberRepository;
import me.kenux.travelog.repository.PasswordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

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
        final Member member = new Member("test", "test@test.com");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        assertThatThrownBy(() -> memberService.joinMember(member, any()))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.EMAIL_DUPLICATION.getMessage());
    }
}
