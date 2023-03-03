package me.kenux.travelog.global.exception;

public class JwtTokenExpiredException extends RuntimeException {

    public JwtTokenExpiredException(Throwable cause) {
        super(cause);
    }
}