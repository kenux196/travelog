package me.kenux.travelog.service.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.kenux.travelog.domain.Member;

@Data
public class MemberJoinRequest {

    private String name;
    private String email;
    private String password;

    public Member toEntity() {
        return new Member(name, email);
    }
}
