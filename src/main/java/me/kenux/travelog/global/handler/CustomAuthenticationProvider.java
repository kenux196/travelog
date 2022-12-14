package me.kenux.travelog.global.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.domain.Member;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // DB에서 사용자 정보가 실제로 유효한지 확인 후 인증된 Authentication 반환
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String userEmail = token.getName();
        String password = (String) token.getCredentials();

        final Member member = (Member) userDetailsService.loadUserByUsername(userEmail);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            log.error("{} 비밀번호 오류", member.getUsername());
            throw new BadCredentialsException(member.getUsername() + " 비밀번호를 확인해 주세요.");
        }
        log.info("{}, 인증 확인됨", member.getUsername());
        return new UsernamePasswordAuthenticationToken(member, password, member.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
