package me.kenux.travelog.global.security.jwt;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
}
