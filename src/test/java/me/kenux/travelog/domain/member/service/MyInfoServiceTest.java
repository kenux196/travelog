package me.kenux.travelog.domain.member.service;

import me.kenux.travelog.domain.member.entity.Member;
import me.kenux.travelog.domain.member.repository.MemberRepository;
import me.kenux.travelog.domain.member.service.dto.response.MyInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MyInfoServiceTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MyInfoService myInfoService;

    private static Authentication authentication;
    private static SecurityContext securityContext;

    @BeforeAll
    static void setup() {
        authentication = Mockito.mock(Authentication.class);
        securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("로그인된 회원의 정보를 조회할 수 있다. - 이름")
    void getMyName_success() {
        // given
        final Member member = Member.builder()
                .email("user@test.com")
                .name("user")
                .build();
        given(securityContext.getAuthentication()).willReturn(authentication);
        given(SecurityContextHolder.getContext().getAuthentication().getName()).willReturn("user@test.com");
        given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

        // when
        final MyInfo.OnlyName myName = myInfoService.getMyName();

        // then
        assertThat(myName).isNotNull();
        assertThat(myName.name()).isEqualTo("user");
    }
}