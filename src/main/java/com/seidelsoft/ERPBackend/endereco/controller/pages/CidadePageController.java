package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.service.CidadeService;
import com.seidelsoft.ERPBackend.endereco.service.EstadoService;
import com.seidelsoft.ERPBackend.system.annotations.PagePrefix;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import com.seidelsoft.ERPBackend.system.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
@PagePrefix("cidades")
public class CidadePageController extends BasePageController<Cidade> {

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private EstadoService estadoService;

    @Override
    protected String getPrefix() {
        return "cidades";
    }

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("cidade", new Cidade());
        model.addAttribute("estados", estadoService.findAll(Sort.by("nome")));
        return "/pages/cidades/adicionar";
    }

    @Override
    public String add(Cidade item) {
        cidadeService.save(item);
        return "redirect:/pages/cidades/consulta";
    }

    @Override
    public String showEditPage(long id, Model model) {
        Cidade cidade = cidadeService.getById(id).orElseThrow();
        model.addAttribute("titulo", "Editar Cidade");
        model.addAttribute("formAction", "/pages/cidades/update/" + id);
        model.addAttribute("item", cidade);
        model.addAttribute("url", "/pages/cidades");
        model.addAttribute("camposFragment", "pages/cidades/cidade-campos");
        return "layouts/editar-base";
    }

    @Override
    public String update(long id, Cidade item) {
        Cidade existente = cidadeService.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cidade inválida"));

        existente.setNome(item.getNome());
        cidadeService.save(existente);
        return "redirect:/pages/cidades/consulta";
    }

    @Override
    public String delete(long id) {
        cidadeService.delete(id);
        return "redirect:/pages/cidades/consulta";
    }

    @Override
    public BaseService<Cidade> getService() {
        return cidadeService;
    }

    @Override
    public String getTitulo() {
        return "Consulta de Cidades";
    }

    @Override
    public String getUrl() {
        return "/pages/cidades";
    }

    @Override
    public String getCabecalhoFragment() {
        return "pages/cidades/_cabecalho";
    }

    @Override
    public String getLinhaFragment() {
        return "pages/cidades/_linha";
    }
}

