package com.seidelsoft.ERPBackend.auth.controller.pages;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginPageController {

    @GetMapping("/login")
    public String login() {
        return "login"; // retorna login.html
    }

    @GetMapping("/")
    public String teste() {
        return "login"; // retorna login.html
    }
}
