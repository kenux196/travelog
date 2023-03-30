package me.kenux.travelog.domain.member.service.dto.response;

import lombok.Data;
import me.kenux.travelog.domain.member.entity.Member;

@Data
public class MyInfo {

    public record OnlyName(String name) {
        public static OnlyName of(Member member) {
            return new OnlyName(member.getName());
        }
    }
}
