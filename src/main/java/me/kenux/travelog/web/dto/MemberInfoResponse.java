package me.kenux.travelog.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MemberInfoResponse {

    private Long id;
    private String name;
    private String email;
//    @DateTimeFormat(pattern = "yyyy-MM")
//    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime joinDate;
    private String status;
}
