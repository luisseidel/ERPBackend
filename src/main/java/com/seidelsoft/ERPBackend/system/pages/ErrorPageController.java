package com.seidelsoft.ERPBackend.system.pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorPageController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = statusObj != null ? Integer.valueOf(statusObj.toString()) : 500;

        model.addAttribute("status", statusCode);
        model.addAttribute("error", request.getAttribute(RequestDispatcher.ERROR_MESSAGE));
        model.addAttribute("message", request.getAttribute("javax.servlet.error.exception"));

        return "error/error";
    }

}
