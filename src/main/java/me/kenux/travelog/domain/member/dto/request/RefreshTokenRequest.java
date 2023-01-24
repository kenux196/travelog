package me.kenux.travelog.domain.member.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
