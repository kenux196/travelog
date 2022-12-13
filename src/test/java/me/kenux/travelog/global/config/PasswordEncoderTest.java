package me.kenux.travelog.global.config;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PasswordEncoderTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("패스워드 암호화 테스트")
    void passwordEncoderTest() {
        // given
        String rawPassword = "1234";

        // when
        final String encodedPassword = passwordEncoder.encode(rawPassword);

        // then
        assertThat(encodedPassword).isNotEqualTo(rawPassword);
        assertThat(passwordEncoder.matches(rawPassword, encodedPassword)).isTrue();
        System.out.println("encodedPassword = " + encodedPassword);
    }

    @Test
    void withDefaultPasswordEncoderTest() {
        final UserDetails user = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("user")
            .build();
        System.out.println("user.getPassword() = " + user.getPassword());
    }

    @Test
    void createDelegatingPasswordEncoderTest() {
        final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        final String password = passwordEncoder.encode("password");
        System.out.println("password = " + password);
    }
}
