package com.seidelsoft.ERPBackend.system.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/{prefix}")
public abstract class BaseConsultaPageController<T> {

    protected abstract String getPrefix();

    @GetMapping("/consulta")
    public String list(@PathVariable String prefix, Model model) {
        if (!prefix.equals(getPrefix())) {
            throw new IllegalArgumentException("Prefix mismatch");
        }
        return listPage(model);
    }

    protected abstract String listPage(Model model);

    @PostMapping(path = "/add")
    public abstract String add(T item);

    @GetMapping(path = "/editar/{id}")
    public abstract String edit(@PathVariable("id") long id, Model model);

    @PostMapping(path = "/update/{id}")
    public abstract String update(@PathVariable("id") long id, T item);

    @GetMapping(path = "/delete/{id}")
    public abstract String delete(@PathVariable("id") long id);
}