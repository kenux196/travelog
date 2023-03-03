package me.kenux.travelog.global.exception;

public class JwtTokenInvalidException extends RuntimeException {

    public JwtTokenInvalidException(Throwable cause) {
        super(cause);
    }
}