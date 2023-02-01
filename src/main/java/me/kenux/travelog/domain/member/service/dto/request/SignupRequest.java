package me.kenux.travelog.domain.member.service.dto.request;

import lombok.Data;

@Data
public class SignupRequest {

    private String name;
    private String email;
    private String password;
}
