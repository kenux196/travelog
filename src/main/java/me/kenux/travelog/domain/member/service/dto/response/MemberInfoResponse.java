package me.kenux.travelog.domain.member.service.dto.response;

import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.member.entity.Member;

import java.time.LocalDateTime;

@Data
@Builder
public class MemberInfoResponse {

    private Long id;
    private String name;
    private String email;
//    @DateTimeFormat(pattern = "yyyy-MM")
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;
    private String status;
    private String role;

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .joinDate(member.getCreatedDate().toLocalDateTime())
            .status(member.getStatus().name())
            .role(member.getUserRole().toString())
            .build();
    }
}
