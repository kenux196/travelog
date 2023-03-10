package me.kenux.travelog.domain.member.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RefreshTokenEntityTest {
    private static final String refreshToken = "valid_token";
    public static final String username = "admin@test.com";

    @Test
    void createRefreshTokenEntity() {
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(refreshToken, username);
        assertThat(refreshTokenEntity).isNotNull();
    }

    @Test
    void updateRefreshToken() {
        final RefreshTokenEntity refreshTokenEntity = new RefreshTokenEntity(refreshToken, username);
        refreshTokenEntity.updateToken("token");
        assertThat(refreshTokenEntity.getToken()).isEqualTo("token");
    }
}