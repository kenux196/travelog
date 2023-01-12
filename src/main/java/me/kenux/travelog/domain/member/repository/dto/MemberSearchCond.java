package me.kenux.travelog.domain.member.repository.dto;

import lombok.Data;
import me.kenux.travelog.domain.member.entity.enums.MemberStatus;

@Data
public class MemberSearchCond {

    private String name;
    private String email;
    private MemberStatus status;
}
