package me.kenux.travelog.global.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import me.kenux.travelog.global.exception.ErrorCode;
import me.kenux.travelog.global.exception.ErrorCustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException {
        log.error("Responding with unauthorized error. Message - {}", authException.getMessage());
        ErrorCustomResponse fail = ErrorCustomResponse.of(ErrorCode.AUTH_UNAUTHORIZED);
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        final ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(fail);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(json);
        writer.flush();
    }
}
