package com.seidelsoft.ERPBackend.system.pages;

import com.seidelsoft.ERPBackend.auth.model.entity.User;
import com.seidelsoft.ERPBackend.system.service.BaseService;
import com.seidelsoft.ERPBackend.system.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.ParameterizedType;

@Controller
public abstract class BasePageController<T, K extends BaseService> {

    private Page<T> items;
    private T item;
    @Autowired
    protected K service;

    @GetMapping("/consulta")
    public String list(
        Model model,
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        addPermissionsToModel(model, getResourceName());
        return listPage(model, page, size);
    }

    protected String listPage(Model model, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
        items = getService().findAllPaged(pageable);
        model.addAttribute("items", items);
        model.addAttribute("title", getListPageTitle());
        model.addAttribute("url", getUrl());
        model.addAttribute("tableHeaderFragment", getTableHeaderFragment());
        model.addAttribute("tableLineFragment", getTableLineFragment());
        return "layouts/baseListPage";
    }

    @GetMapping(path = "/adicionar")
    public String showAddPage(Model model) {
        addPermissionsToModel(model, getResourceName());
        model.addAttribute("title", getAddPageTitle());
        model.addAttribute("addFormAction", getAddFormAction());
        model.addAttribute("item", getItem());
        model.addAttribute("url", getUrl());
        model.addAttribute("addFieldsFragment", getAddFieldsFragment());
        return "layouts/baseAddPage";
    }

    @GetMapping(path = "/editar/{id}")
    public String showEditPage(@PathVariable("id") long id, Model model) {
        addPermissionsToModel(model, getResourceName());
        item = getService().getById(id).orElseThrow();
        model.addAttribute("title", getEditPageTitle());
        model.addAttribute("formAction", getUpdateFormAction().replace("{id}", String.valueOf(id)));
        model.addAttribute("item", item);
        model.addAttribute("url", getUrl());
        model.addAttribute("editFieldFragment", getEditFieldsFragment());
        return "layouts/baseEditPage";
    }

    @PostMapping(path = "/add")
    public String add(T item) {
        getService().save(item);
        return getUrlPageConsulta();
    }

    @PostMapping(path = "/update/{id}")
    public String update(@PathVariable("id") long id, T item) {
        getService().save(item);
        return getUrlPageConsulta();
    }

    @GetMapping(path = "/delete/{id}")
    public String delete(@PathVariable("id") long id) {
        getService().delete(id);
        return getUrlPageConsulta();
    }

    protected String getResourceName() {
        ParameterizedType superclass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<?> entityClass = (Class<?>) superclass.getActualTypeArguments()[0];
        return entityClass.getSimpleName().toUpperCase();
    }

    public abstract IService<T> getService();
    public abstract String getListPageTitle();
    public abstract String getEditPageTitle();
    public abstract String getAddPageTitle();
    public abstract String getUrl();
    public abstract T getItem();

    public String getTableHeaderFragment() {
        return getUrl() + "/_tableHeaderFragment";
    }
    public String getTableLineFragment() {
        return getUrl() + "/_tableLineFragment";
    }
    public String getAddFieldsFragment() {
        return getUrl() + "/_addFieldsFragment";
    }
    public String getEditFieldsFragment() {
        return getUrl() + "/_editFieldsFragment";
    }
    public String getAddFormAction() {
        return getUrl() + "/add";
    }
    public String getUpdateFormAction() {
        return getUrl() + "/update/{id}";
    }
    public String getUrlPageConsulta() {
        return "redirect:"+getUrl()+"/consulta";
    }

    protected void addPermissionsToModel(Model model, String resourceName) {
        model.addAttribute("canConsultar", canConsultar(resourceName));
        model.addAttribute("canAdicionar", canAdicionar(resourceName));
        model.addAttribute("canEditar", canEditar(resourceName));
        model.addAttribute("canExcluir", canExcluir(resourceName));
    }

    protected boolean canConsultar(String resourceName) {
        return hasPermission(resourceName, "consultar");
    }

    protected boolean canAdicionar(String resourceName) {
        return hasPermission(resourceName, "adicionar");
    }

    protected boolean canEditar(String resourceName) {
        return hasPermission(resourceName, "editar");
    }

    protected boolean canExcluir(String resourceName) {
        return hasPermission(resourceName, "excluir");
    }

    private boolean hasPermission(String resourceName, String action) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) return false;

        return user.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .filter(rp -> rp.getPermission().getName().equalsIgnoreCase(resourceName))
                .anyMatch(rp -> switch (action.toLowerCase()) {
                    case "consultar" -> rp.isConsultar();
                    case "adicionar" -> rp.isAdicionar();
                    case "editar" -> rp.isEditar();
                    case "excluir" -> rp.isExcluir();
                    default -> false;
                });
    }

}