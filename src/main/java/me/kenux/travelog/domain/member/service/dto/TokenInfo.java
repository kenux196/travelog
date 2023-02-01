package me.kenux.travelog.domain.member.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenInfo {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String role;
}
