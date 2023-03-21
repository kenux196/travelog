package me.kenux.travelog.domain.member.service.dto.response;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.member.entity.Member;

import java.time.OffsetDateTime;

@Data
@Builder
public class MemberInfo {

    public record SimpleResponse(Long id, String name, String email, OffsetDateTime joinDate) {
        public static SimpleResponse of(Member member) {
            return new SimpleResponse(member.getId(), member.getName(), member.getEmail(), member.getCreatedDate());
        }
    }

    @Builder
    public record DetailResponse(Long id, String name, String email, OffsetDateTime joinDate, String status, String role) {

        public static DetailResponse of(Member member) {
            return DetailResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .joinDate(member.getCreatedDate())
                .status(member.getStatus().name())
                .role(member.getUserRole().toString())
                .build();
        }
    }
}
