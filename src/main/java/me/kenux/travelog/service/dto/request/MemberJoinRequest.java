package me.kenux.travelog.service.dto.request;

import lombok.Data;

@Data
public class MemberJoinRequest {

    private String name;
    private String email;
    private String password;
}