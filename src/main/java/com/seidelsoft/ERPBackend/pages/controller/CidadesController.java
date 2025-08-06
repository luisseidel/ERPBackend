package com.seidelsoft.ERPBackend.pages.controller;

import com.seidelsoft.ERPBackend.model.entity.Cidade;
import com.seidelsoft.ERPBackend.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pages")
public class CidadesController {

    @Autowired
    CidadeRepository cidadeRepository;

    @GetMapping("/cidades")
    public String listarCidades(Model model) {
        List<Cidade> cidades = cidadeRepository.findAll();
        model.addAttribute("cidades", cidades);
        return "cidades";
    }
}

