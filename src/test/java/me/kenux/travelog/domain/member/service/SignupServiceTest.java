package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.repository.PasswordRepository;
import me.kenux.travelog.domain.member.service.dto.request.SignupRequest;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class SignupServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    PasswordRepository passwordRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    SignupService signupService;

    @Test
    @DisplayName("회원 중복인 경우 예외 발생해야 한다.")
    void duplicated_member_test() {
        // given
        SignupRequest request = new SignupRequest();
        request.setName("testUser1");
        request.setEmail("testUser1@email.com");
        request.setPassword("password");
        given(memberRepository.existsByEmail(any())).willReturn(true);

        assertThatThrownBy(() -> signupService.signup(request))
            .isInstanceOf(CustomException.class)
            .hasMessage(ErrorCode.MEMBER_ALREADY_EXIST.getMessage());
    }

    @Test
    @DisplayName("회원 가입 성공")
    void save_password_success_join_member() {
        // given
        SignupRequest request = new SignupRequest();
        request.setName("testUser1");
        request.setEmail("testUser1@email.com");
        request.setPassword("password");
        given(memberRepository.existsByEmail(any())).willReturn(false);
        given(passwordEncoder.encode(any())).willReturn(anyString());

        // when
        signupService.signup(request);

        then(passwordEncoder).should(times(1)).encode(any());
        then(memberRepository).should(times(1)).save(any());
        then(passwordRepository).should(times(1)).save(any());
    }

}
