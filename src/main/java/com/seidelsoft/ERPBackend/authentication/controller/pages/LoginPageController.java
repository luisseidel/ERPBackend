package com.seidelsoft.ERPBackend.authentication.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @GetMapping("/")
    public String root() {
        return "redirect:/auth/login";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

}
