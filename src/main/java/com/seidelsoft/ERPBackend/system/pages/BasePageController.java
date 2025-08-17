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

import com.seidelsoft.ERPBackend.system.service.BaseService;

@Controller
@RequestMapping("/pages/{prefix}")
public abstract class BasePageController<T> {

    private Page<T> items;

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
        model.addAttribute("titulo", getTitulo());
        model.addAttribute("url", getUrl());
        model.addAttribute("cabecalhoFragment", getCabecalhoFragment());
        model.addAttribute("linhaFragment", getLinhaFragment());
        return "layouts/consulta-base";
    }

    @GetMapping(path = "/adicionar")
    public abstract String showAddPage(Model model);

    @PostMapping(path = "/add")
    public abstract String add(T item);

    @GetMapping(path = "/editar/{id}")
    public abstract String showEditPage(@PathVariable("id") long id, Model model);

    @PostMapping(path = "/update/{id}")
    public abstract String update(@PathVariable("id") long id, T item);

    @GetMapping(path = "/delete/{id}")
    public abstract String delete(@PathVariable("id") long id);

    public abstract BaseService<T> getService();
    public abstract String getTitulo();
    public abstract String getUrl();
    public abstract String getCabecalhoFragment();
    public abstract String getLinhaFragment();
}