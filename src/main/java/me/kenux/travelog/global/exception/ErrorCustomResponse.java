package me.kenux.travelog.global.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ErrorCustomResponse {

    private final int status;
    private final String type;
    private final String message;
    private final String code;

    public static ResponseEntity<ErrorCustomResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ErrorCustomResponse.of(errorCode));
    }

    public static ErrorCustomResponse of(ErrorCode errorCode) {
        return ErrorCustomResponse.builder()
            .status(errorCode.getHttpStatus().value())
            .type(errorCode.name())
            .message(errorCode.getMessage())
            .code(errorCode.getCode())
            .build();
    }
}
