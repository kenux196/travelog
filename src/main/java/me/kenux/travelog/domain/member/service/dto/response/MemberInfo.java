package me.kenux.travelog.domain.member.service.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.member.entity.Member;

import java.time.OffsetDateTime;

@Schema(description = "회원 정보 응답 메시지")
@Data
@Builder
public class MemberInfo {

    @Schema(description = "심플 회원 정보 응답 메시지")
    public record SimpleResponse(Long id, String name, String email, OffsetDateTime joinDate) {
        public static SimpleResponse of(Member member) {
            return new SimpleResponse(member.getId(), member.getName(), member.getEmail(), member.getCreatedDate());
        }
    }

    @Schema(description = "상세 회원 정보 응답 메시지")
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
