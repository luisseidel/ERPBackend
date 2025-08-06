package com.seidelsoft.ERPBackend.pages.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login() {
        return "login"; // retorna login.html
    }

    @GetMapping("/teste")
    public String teste() {
        return "login"; // retorna login.html
    }
}
