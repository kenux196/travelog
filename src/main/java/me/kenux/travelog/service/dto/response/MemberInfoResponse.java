package me.kenux.travelog.service.dto.response;

import lombok.Builder;
import lombok.Data;

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
}
