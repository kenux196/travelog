package me.kenux.travelog.domain.member.service.dto.request;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String token;
}
