package com.seidelsoft.ERPBackend.authorization.controller.pages;

import com.seidelsoft.ERPBackend.authorization.entity.RolePermission;
import com.seidelsoft.ERPBackend.authorization.service.RolePermissionService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/rolepermission")
public class RolePermissionPageController extends BasePageController<RolePermission, RolePermissionService> {

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("roles", getService().findAllRoles());
        model.addAttribute("permissions", getService().findAllPermission());
        return super.showAddPage(model);
    }

    @Override
    public String showEditPage(long id, Model model) {
        model.addAttribute("roles", getService().findAllRoles());
        model.addAttribute("permissions", getService().findAllPermission());
        return super.showEditPage(id, model);
    }

    @Override
    public RolePermissionService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta de Permissões x Roles";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Permissão x Roles";
    }

    @Override
    public String getAddPageTitle() {
        return "Permissão x Roles";
    }

    @Override
    public String getUrl() {
        return "/pages/rolepermission";
    }

    @Override
    public RolePermission getItem() {
        return new RolePermission();
    }
}
