package me.kenux.travelog.domain.member.repository.dto;

import lombok.Data;

@Data
public class MemberSearchCond {

    private String name;
    private String email;
}
