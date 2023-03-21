package me.kenux.travelog.domain.member.service.dto.response;

import lombok.Data;
import me.kenux.travelog.domain.member.entity.Member;

@Data
public class MyInfo {

    public record Simple(String name) {
        public static Simple of(Member member) {
            return new Simple(member.getName());
        }
    }
}
