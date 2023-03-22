package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.entity.UserPassword;
import me.kenux.travelog.domain.member.entity.enums.UserRole;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("username으로 UserDetails 가져온다. - 성공")
    void loadUserByUsername() {
        // given
        String username = "member1@test.com";
        final UserPassword password = new UserPassword("1");
        final Member member = Member.builder()
                .email(username)
                .name("member1")
                .userRole(UserRole.USER)
                .password(password)
                .build();
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        // when
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // then
        assertThat(userDetails).isNotNull();
    }

    @Test
    @DisplayName("username에 해당하는 사용자 없으면, 예외발생")
    void loadUserByUsername_exception() {
        // given
        given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

        // when then
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername("NoMember@test.com"))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorCode.AUTH_UNREGISTERED_MEMBER.getMessage());
    }

}