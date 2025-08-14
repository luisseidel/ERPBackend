package com.seidelsoft.ERPBackend.system.pages;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/consulta")
public abstract class BaseConsultaPageController<T> {

    protected abstract String getPrefix();

    @GetMapping("")
    public String redirectToConsultaPath() {
        return "redirect:/pages/consulta/" + getPrefix() + "/";
    }

    @GetMapping("/{prefix}")
    public String list(@PathVariable String prefix, Model model) {
        if (!prefix.equals(getPrefix())) {
            throw new IllegalArgumentException("Prefix mismatch");
        }
        return listPage(model);
    }

    protected abstract String listPage(Model model);


    @GetMapping(path = "/{prefix}/add")
    public abstract String showAddForm(T item);

    @GetMapping(path = "/{prefix}/edit/{id}")
    public abstract String showUpdateForm(@PathVariable("id") long id, Model model);

    @PostMapping(path = "/{prefix}/add")
    public abstract String add(T item);

    @PostMapping(path = "/{prefix}/update/{id}")
    public abstract String update(@PathVariable("id") long id, T item);

    @GetMapping(path = "/{prefix}/delete/{id}")
    public abstract String delete(@PathVariable("id") long id);
}