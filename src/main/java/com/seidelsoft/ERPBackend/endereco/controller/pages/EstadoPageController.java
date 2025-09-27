package com.seidelsoft.ERPBackend.endereco.controller.pages;

import com.seidelsoft.ERPBackend.endereco.model.Estado;
import com.seidelsoft.ERPBackend.endereco.service.EstadoService;
import com.seidelsoft.ERPBackend.endereco.service.PaisService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pages/estados")
public class EstadoPageController extends BasePageController<Estado, EstadoService> {

    private final PaisService paisService;

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("paises", paisService.findAll(Sort.by("nome")));
        return super.showAddPage(model);
    }

    @Override
    public String update(long id, Estado item) {
        Estado existente = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estado inválida"));

        existente.setNome(item.getNome());
        service.save(existente);
        return getUrlPageConsulta();
    }

    public EstadoService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta de Estados";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Estado";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Estado";
    }

    @Override
    public String getUrl() {
        return "/pages/estados";
    }

    @Override
    public Estado getItem() {
        return new Estado();
    }
}
