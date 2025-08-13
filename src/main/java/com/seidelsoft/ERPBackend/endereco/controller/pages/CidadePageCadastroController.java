package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.service.CidadeService;
import com.seidelsoft.ERPBackend.system.annotations.PagePrefix;
import com.seidelsoft.ERPBackend.system.pages.BaseConsultaPageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@PagePrefix("cidades")
public class CidadePageCadastroController extends BaseConsultaPageController<Cidade> {

    @Autowired
    private CidadeService cidadeService;

    @Override
    protected String getPrefix() {
        return "cidades";
    }

    @Override
    protected String listPage(Model model) {
        List<Cidade> cidades = cidadeService.findAll();
        model.addAttribute("cidades", cidades);
        return "pages/consulta/cidades";
    }

    @Override
    public String showAddForm(Cidade item) {
        return "cidade-form";
    }

    @Override
    public String showUpdateForm(long id, Model model) {
        Cidade c = cidadeService.getById(id).orElseThrow(() -> new IllegalArgumentException("Inválido"));
        model.addAttribute("cidade", c);
        return "cidade-form";
    }

    @Override
    public String add(Cidade item) {
        cidadeService.save(item);
        return "redirect:/pages/cidades";
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

