package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Endereco;
import com.seidelsoft.ERPBackend.endereco.service.CidadeService;
import com.seidelsoft.ERPBackend.endereco.service.EnderecoService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/enderecos")
public class EnderecoPageController extends BasePageController<Endereco, EnderecoService> {

    @Autowired
    private CidadeService cidadeService;

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("cidades", cidadeService.findAll(Sort.by("nome")));
        return super.showAddPage(model);
    }

    @Override
    public String update(long id, Endereco item) {
        Endereco existente = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Endereco inválida"));

        existente.setCep(item.getCep());
        existente.setBairro(item.getBairro());
        existente.setNumero(item.getNumero());
        existente.setLogradouro(item.getLogradouro());
        existente.setPontoReferencia(item.getPontoReferencia());

        service.save(existente);
        return getUrlPageConsulta();
    }

    @Override
    public EnderecoService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta de Endereços";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Endereço";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Endereço";
    }

    @Override
    public String getUrl() {
        return "/pages/enderecos";
    }

    @Override
    public Endereco getItem() {
        return new Endereco();
    }
}
