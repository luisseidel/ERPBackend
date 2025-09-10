package com.seidelsoft.ERPBackend.auth.controller.pages;

import com.seidelsoft.ERPBackend.auth.model.entity.User;
import com.seidelsoft.ERPBackend.auth.service.RoleService;
import com.seidelsoft.ERPBackend.auth.service.UserService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/usuarios")
public class UsuarioPageController extends BasePageController<User, UserService> {

    @Autowired
    private RoleService roleService;

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("roles", roleService.findAll(Sort.by("name")));
        return super.showAddPage(model);
    }

    @Override
    public String showEditPage(long id, Model model) {
        model.addAttribute("roles", roleService.findAll(Sort.by("name")));
        return super.showEditPage(id, model);
    }

    @Override
    public String update(long id, User item) {
        User existente = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Usuário Não encontrado!"));
        existente.setName(item.getName());
        existente.setEmail(item.getEmail());
        existente.setPassword(item.getPassword());
        existente.setRoles(item.getRoles());
        service.save(existente);
        return getUrlPageConsulta();
    }

    @Override
    public UserService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta Usuários";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Usuário";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Usuário";
    }

    @Override
    public String getUrl() {
        return "/pages/usuarios";
    }

    @Override
    public User getItem() {
        return new User();
    }
}
