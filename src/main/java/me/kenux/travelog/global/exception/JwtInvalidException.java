package me.kenux.travelog.global.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtInvalidException extends AuthenticationException {


    public JwtInvalidException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtInvalidException(String msg) {
        super(msg);
    }
}