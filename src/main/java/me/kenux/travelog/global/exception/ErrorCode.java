package me.kenux.travelog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // auth
    AUTH_UNAUTHORIZED(UNAUTHORIZED, "A001","인증되지 않은 사용자입니다."),
    AUTH_WRONG_PASSWORD(BAD_REQUEST, "A002","패스워드가 일치하지 않습니다."),
    AUTH_MEMBER_NOT_EXIST(NOT_FOUND, "A003", "회원 정보를 찾을 수 없습니다."),

    // Token
    AUTH_ACCESS_TOKEN_EXPIRED(UNAUTHORIZED, "A004", "Access token expired."),
    AUTH_REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, "A005", "Refresh token expired."),

    // member
    MEMBER_ALREADY_EXIST(BAD_REQUEST, "M001","이미 가입된 이메일 주소입니다."),
    MEMBER_PASSWORD_SHORT(BAD_REQUEST, "M002", "패스워드는 8자 이상이어야 합니다."),
    MEMBER_NOT_EXIST(NOT_FOUND, "M003", "요청한 멤버가 존재하지 않습니다."),

    BOOK_NOT_FOUND(NOT_FOUND, "B001", "책 정보가 존재하지 않습니다."),
    BOOK_WAS_RENTED(BAD_REQUEST, "B002", "해당 책은 대출 상태입니다. 다시 확인하세요."),

    RENT_INFO_NOT_FOUND(NOT_FOUND, "BR001", "대출 정보가 존재하지 않습니다."),
    RENT_INFO_DUPLICATION(BAD_REQUEST, "BR002", "동일한 대출 정보가 존재합니다."),

    PARAMETER_WRONG(BAD_REQUEST, "C001", "파라미터 값이 잘못되었습니다."),
    DATA_NOT_FOUND(NOT_FOUND, "C002", "데이터가 없습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
