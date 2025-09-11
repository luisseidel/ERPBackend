package com.seidelsoft.ERPBackend.auth.controller.pages;

import com.seidelsoft.ERPBackend.auth.model.entity.Role;
import com.seidelsoft.ERPBackend.auth.service.RoleService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/roles")
public class RolePageController extends BasePageController<Role, RoleService> {


    @Override
    public RoleService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta Roles de Usuários";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Roles de Usuários";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Role de Usuário";
    }

    @Override
    public String getUrl() {
        return "/pages/roles";
    }

    @Override
    public Role getItem() {
        return new Role();
    }
}
