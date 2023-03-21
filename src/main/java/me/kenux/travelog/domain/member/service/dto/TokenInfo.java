package me.kenux.travelog.domain.member.service.dto;

import lombok.Builder;

public class TokenInfo {

    @Builder
    public record Full(Long userId, String grantType, String accessToken, String refreshToken, String role) {}

    @Builder
    public record AccessToken(String accessToken) {}
}
