package me.kenux.travelog.global.config;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

class SecurityTest {

    @Test
    void settingSecurityContextHolder() {
        SecurityContext newContext = SecurityContextHolder.createEmptyContext();
        Authentication newAuthentication =
            new TestingAuthenticationToken("username", "password", "ROLE_USER", "ROLE_ADMIN");
        newContext.setAuthentication(newAuthentication);

        SecurityContextHolder.setContext(newContext);

        final SecurityContext context = SecurityContextHolder.getContext();
        final Authentication authentication = context.getAuthentication();
        final String username = authentication.getName();
        final Object principal = authentication.getPrincipal();
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        assertThat(username).isEqualTo("username");
        assertThat(principal).isEqualTo("username");

        System.out.println("username = " + username);
        System.out.println("principal = " + principal);
        System.out.println("authorities = " + authorities);
    }
}
