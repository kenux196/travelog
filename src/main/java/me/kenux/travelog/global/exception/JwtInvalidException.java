package me.kenux.travelog.global.exception;

public class JwtInvalidException extends RuntimeException {

    public JwtInvalidException(Throwable cause) {
        super(cause);
    }
}