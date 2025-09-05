package com.seidelsoft.ERPBackend.menu.controller;

import com.seidelsoft.ERPBackend.menu.model.Menu;
import com.seidelsoft.ERPBackend.menu.service.MenuService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/menus")
public class MenuPageController extends BasePageController<Menu, MenuService> {

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("parentMenus", service.findAll(Sort.by("orderPosition", "name")));
        return super.showAddPage(model);
    }

    @Override
    public String showEditPage(long id, Model model) {
        model.addAttribute("parentMenus", service.findAll(Sort.by("orderPosition", "name"))
                .stream()
                .filter(menu -> !menu.getId().equals(id) && !isDescendant(menu, id))
                .toList());
        return super.showEditPage(id, model);
    }

    @Override
    public String update(long id, Menu item) {
        Menu existente = service.getById(id)
            .orElseThrow(() -> new IllegalArgumentException("Menu inválido"));

        existente.setName(item.getName());
        existente.setUrl(item.getUrl());
        existente.setParent(item.getParent());
        existente.setOrderPosition(item.getOrderPosition());
        existente.setActive(item.getActive());
        existente.setHomePage(item.getHomePage());
        existente.setIcon(item.getIcon());
        existente.setDescription(item.getDescription());

        service.save(existente);
        return getUrlPageConsulta();
    }

    @Override
    public MenuService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta de Menus";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Menu";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Menu";
    }

    @Override
    public String getUrl() {
        return "/pages/menus";
    }

    @Override
    public Menu getItem() {
        return new Menu();
    }

    /**
     * Verifica se um menu é descendente de outro (para evitar referência circular)
     */
    private boolean isDescendant(Menu menu, Long ancestorId) {
        if (menu.getParent() == null) {
            return false;
        }
        if (menu.getParent().getId().equals(ancestorId)) {
            return true;
        }
        return isDescendant(menu.getParent(), ancestorId);
    }
}
