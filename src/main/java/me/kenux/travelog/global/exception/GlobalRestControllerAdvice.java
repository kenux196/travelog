package me.kenux.travelog.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static me.kenux.travelog.global.exception.GlobalRestControllerAdvice.ORDER;

@Slf4j
@Order(ORDER)
@RestControllerAdvice(annotations = RestController.class)
public class GlobalRestControllerAdvice extends ResponseEntityExceptionHandler {

    public static final int ORDER = 0;

    @ExceptionHandler(CustomException.class)
    protected ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        log.error("handleCustomException throw CustomException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }
}
