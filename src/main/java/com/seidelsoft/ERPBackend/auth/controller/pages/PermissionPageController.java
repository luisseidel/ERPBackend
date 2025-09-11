package com.seidelsoft.ERPBackend.auth.controller.pages;

import com.seidelsoft.ERPBackend.auth.model.entity.Permission;
import com.seidelsoft.ERPBackend.auth.service.PermissionService;
import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/permissoes")
public class PermissionPageController extends BasePageController<Permission, PermissionService> {

    @Override
    public PermissionService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Consulta Permissões";
    }

    @Override
    public String getEditPageTitle() {
        return "Editar Permissão";
    }

    @Override
    public String getAddPageTitle() {
        return "Adicionar Permissão";
    }

    @Override
    public String getUrl() {
        return "/pages/permissoes";
    }

    @Override
    public Permission getItem() {
        return new Permission();
    }
}
