package me.kenux.travelog.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(CustomException.class)
    protected String handleCustomException(RedirectAttributes redirectAttributes, CustomException e) {
        redirectAttributes.addFlashAttribute("exception", e);
        return "redirect:/error";
    }
}
