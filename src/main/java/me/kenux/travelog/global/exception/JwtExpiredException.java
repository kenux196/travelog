package me.kenux.travelog.global.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredException extends AuthenticationException {

    public JwtExpiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public JwtExpiredException(String msg) {
        super(msg);
    }
}