package me.kenux.travelog.domain.member.entity;

import me.kenux.travelog.domain.member.entity.enums.UserRole;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RefreshTokenEntityTest {

    @Test
    void createRefreshTokenEntity() {
        // given
        Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .userRole(UserRole.USER)
                .build();

        // when
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(member);

        // then
        assertThat(refreshTokenEntity).isNotNull();
    }

    @Test
    void updateRefreshToken() {
        Member member = Member.builder()
                .name("user")
                .email("user@test.com")
                .userRole(UserRole.USER)
                .build();
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(member);

        // when
        refreshTokenEntity.updateToken("token");

        // then
        assertThat(refreshTokenEntity.getToken()).isEqualTo("token");
    }
}