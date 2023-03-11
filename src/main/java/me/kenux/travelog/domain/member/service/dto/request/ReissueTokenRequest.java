package me.kenux.travelog.domain.member.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ReissueTokenRequest {
    @NotBlank
    private String token;
}
