package me.kenux.travelog.service.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import me.kenux.travelog.domain.Member;
import org.springframework.format.annotation.DateTimeFormat;

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

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
            .id(member.getId())
            .name(member.getName())
            .email(member.getEmail())
            .joinDate(member.getCreatedDate().toLocalDateTime())
            .status(member.getStatus().name())
            .build();
    }
}
