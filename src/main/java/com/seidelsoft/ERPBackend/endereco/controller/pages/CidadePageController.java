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
        model.addAttribute("estados", estadoService.findAll(Sort.by("nome")));
        return super.showAddPage(model);
    }

    @Override
    public String add(Cidade item) {
        cidadeService.save(item);
        return "redirect:/pages/cidades/consulta";
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
    public String getListPageTitle() {
        return "Consulta de Cidades";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Cidade";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Cidade";
    }

    @Override
    public String getUrl() {
        return "/pages/cidades";
    }

    @Override
    public String getTableHeaderFragment() {
        return "pages/cidades/_tableHeaderFragment";
    }

    @Override
    public String getTableLineFragment() {
        return "pages/cidades/_tableLineFragment";
    }

    @Override
    public String getAddFieldsFragment() {
        return "pages/cidades/_addFieldsFragment";
    }

    @Override
    public String getEditFieldsFragment() {
        return "pages/cidades/_editFieldsFragment";
    }

    @Override
    public String getAddFormAction() {
        return "/pages/cidades/add";
    }

    @Override
    public String getUpdateFormAction() {
        return "/pages/cidades/update/{id}";
    }
}

