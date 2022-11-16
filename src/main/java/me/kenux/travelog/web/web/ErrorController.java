package me.kenux.travelog.web.web;

import me.kenux.travelog.global.exception.CustomException;
import me.kenux.travelog.global.exception.ErrorInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/error")
public class ErrorController {

    // https://dev-overload.tistory.com/29
    // https://hongs-coding.tistory.com/118

    @GetMapping
    public String error404(Model model, @ModelAttribute CustomException e) {

        final ModelAndView mv = new ModelAndView("/view/error/error_400");
//        mv.addObject("status", e.getErrorCode().getHttpStatus().value());
//        mv.ad
//        return mv;
        model.addAttribute("status", e.getErrorCode().getHttpStatus().value());
        model.addAttribute("message", e.getMessage());
        return "/view/error/error_400";
    }
}
