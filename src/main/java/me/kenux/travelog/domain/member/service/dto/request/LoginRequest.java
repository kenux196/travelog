package me.kenux.travelog.domain.member.service.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}