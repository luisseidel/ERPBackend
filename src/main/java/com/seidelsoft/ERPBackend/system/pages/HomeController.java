package com.seidelsoft.ERPBackend.system.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public class HomeController {

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("mensagem", "Bem-vindo ao Spring com Thymeleaf!");
        return "/pages/home"; // Vai procurar o arquivo "home.html" em /templates
    }
}
