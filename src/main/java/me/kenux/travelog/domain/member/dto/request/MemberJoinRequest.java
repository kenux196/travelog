package me.kenux.travelog.domain.member.dto.request;

import lombok.Data;

@Data
public class MemberJoinRequest {

    private String name;
    private String email;
    private String password;
}
