package me.kenux.travelog.web.common;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequestMapping("/error")
public class CustomErrorController implements ErrorController {

    @GetMapping
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == NOT_FOUND.value()) {
                return "view/error/error-404";
            } else if (statusCode == BAD_REQUEST.value()) {
                return "view/error/error-400";
            } else if (statusCode == INTERNAL_SERVER_ERROR.value()) {
                return "view/error/error-500";
            }
        }
        return "view/error/error";
    }
}
