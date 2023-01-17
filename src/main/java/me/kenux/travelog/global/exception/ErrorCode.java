package me.kenux.travelog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    EMAIL_DUPLICATION(BAD_REQUEST, "M001","이미 가입된 이메일 주소입니다."),
    PASSWORD_SHORT(BAD_REQUEST, "M002", "패스워드는 8자 이상이어야 합니다."),
    PASSWORD_WRONG(BAD_REQUEST, "M003","패스워드가 틀립니다."),
    MEMBER_NOT_FOUND(NOT_FOUND, "M004", "가입된 멤버를 찾을 수 없습니다."),

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
