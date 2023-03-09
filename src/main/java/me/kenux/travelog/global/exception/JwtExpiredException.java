package me.kenux.travelog.global.exception;

public class JwtExpiredException extends RuntimeException {

    public JwtExpiredException(Throwable cause) {
        super(cause);
    }
}