package com.seidelsoft.ERPBackend.system.pages;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.seidelsoft.ERPBackend.endereco.model.Cidade;
import com.seidelsoft.ERPBackend.system.service.BaseService;

@Controller
@RequestMapping("/pages/{prefix}")
public abstract class BasePageController<T> {

    private Page<T> items;
    private T item;

    protected abstract String getPrefix();

    @GetMapping("/consulta")
    public String list(
        @PathVariable String prefix, 
        Model model,
        @RequestParam(required = false, defaultValue = "0") int page, 
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        if (!prefix.equals(getPrefix())) {
            throw new IllegalArgumentException("Prefix mismatch");
        }
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
        return "layouts/consulta-base";
    }

    @GetMapping(path = "/adicionar")
    public String showAddPage(Model model) {
        model.addAttribute("title", getAddPageTitle());
        model.addAttribute("addFormAction", getAddFormAction());
        model.addAttribute("item", new Cidade());
        model.addAttribute("url", getUrl());
        model.addAttribute("addFieldsFragment", getAddFieldsFragment());
        return "layouts/adicionar-base";
    }

    @PostMapping(path = "/add")
    public abstract String add(T item);

    @GetMapping(path = "/editar/{id}")
    public String showEditPage(@PathVariable("id") long id, Model model) {
        item = getService().getById(id).orElseThrow();
        model.addAttribute("title", getEditPageTitle());
        model.addAttribute("formAction", getUpdateFormAction().replace("{id}", String.valueOf(id)));
        model.addAttribute("item", item);
        model.addAttribute("url", getUrl());
        model.addAttribute("editFieldFragment", getEditFieldsFragment());
        return "layouts/editar-base";
    }

    @PostMapping(path = "/update/{id}")
    public abstract String update(@PathVariable("id") long id, T item);

    @GetMapping(path = "/delete/{id}")
    public abstract String delete(@PathVariable("id") long id);

    public abstract BaseService<T> getService();
    public abstract String getListPageTitle();
    public abstract String getEditPageTitle();
    public abstract String getAddPageTitle();
    public abstract String getUrl();
    public abstract String getTableHeaderFragment();
    public abstract String getTableLineFragment();
    public abstract String getAddFieldsFragment();
    public abstract String getEditFieldsFragment();
    public abstract String getAddFormAction();
    public abstract String getUpdateFormAction();
}