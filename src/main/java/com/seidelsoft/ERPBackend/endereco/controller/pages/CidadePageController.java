package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.endereco.service.CidadeService;
import com.seidelsoft.ERPBackend.endereco.service.EstadoService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import com.seidelsoft.ERPBackend.system.service.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/pages/cidades")
public class CidadePageController extends BasePageController<Cidade> {

    @Autowired
    private CidadeService cidadeService;
    @Autowired
    private EstadoService estadoService;



    @Override
    public String showAddPage(Model model) {
        model.addAttribute("estados", estadoService.findAll(Sort.by("nome")));
        return super.showAddPage(model);
    }

    @Override
    public String add(Cidade item) {
        cidadeService.save(item);
        return getUrlPageConsulta();
    }

    @Override
    public String update(long id, Cidade item) {
        Cidade existente = cidadeService.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("Cidade inválida"));

        existente.setNome(item.getNome());
        cidadeService.save(existente);
        return getUrlPageConsulta();
    }

    @Override
    public String delete(long id) {
        cidadeService.delete(id);
        return getUrlPageConsulta();
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
    public Cidade getItem() {
        return new Cidade();
    }

}

