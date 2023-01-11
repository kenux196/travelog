package me.kenux.travelog.global.security.jwt;

import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.ErrorCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException, ServletException {

        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());

//        final ErrorCode unAuthorizationCode = (ErrorCode) request.getAttribute("unauthorization.code");
//        request.setAttribute("response.failure.code", unAuthorizationCode.name());
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
