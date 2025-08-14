package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.service.CidadeService;
import com.seidelsoft.ERPBackend.system.annotations.PagePrefix;
import com.seidelsoft.ERPBackend.system.pages.BaseConsultaPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@PagePrefix("cidades")
public class CidadePageController extends BaseConsultaPageController<Cidade> {

    @Autowired
    private CidadeService cidadeService;

    @Override
    protected String getPrefix() {
        return "cidades";
    }

    @Override
    protected String listPage(Model model, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        Page<Cidade> cidades = cidadeService.findAll(pageable);
        model.addAttribute("cidades", cidades);
        return "pages/cidades/consulta"; 
    }

    @Override
    public String add(Cidade item) {
        cidadeService.save(item);
        return "redirect:/pages/cidades";
    }

    @Override
    public String edit(long id, Model model) {
        Optional<Cidade> cidade = cidadeService.getById(id);
        if (cidade.isPresent()) {
            model.addAttribute("cidade", cidade.get());
            return "/pages/cidades/editar";
        } else {
            return "redirect:/pages/cidades";
        }
    }

    @Override
    public String update(long id, Cidade item) {
        item.setId(id);
        cidadeService.save(item);
        return "redirect:/pages/cidades";
    }

    @Override
    public String delete(long id) {
        Cidade c = cidadeService.getById(id).orElseThrow(() -> new IllegalArgumentException("Inválido"));
        cidadeService.delete(id);
        return "redirect:/pages/cidades";
    }
}

