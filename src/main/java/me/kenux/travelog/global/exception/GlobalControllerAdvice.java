package me.kenux.travelog.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@Order(GlobalRestControllerAdvice.ORDER + 1)
@ControllerAdvice
public class GlobalControllerAdvice {
    @ExceptionHandler(CustomException.class)
    protected String handleCustomException(CustomException e) {
        log.error(e.getMessage(), e);
        if (e.getErrorCode().getHttpStatus() == NOT_FOUND) {
            return "view/error/error-404";
        } else if (e.getErrorCode().getHttpStatus() == BAD_REQUEST) {
            return "view/error/error-400";
        } else if (e.getErrorCode().getHttpStatus() == INTERNAL_SERVER_ERROR) {
            return "view/error/error-500";
        }
        return "view/error/error";
    }
}
