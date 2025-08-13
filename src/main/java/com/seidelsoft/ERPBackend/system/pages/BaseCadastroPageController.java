package com.seidelsoft.ERPBackend.system.pages;

import com.seidelsoft.ERPBackend.system.annotations.PagePrefix;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages")
public abstract class BaseCadastroPageController<T> {

    private String getPrefix() {
        PagePrefix annotation = this.getClass().getAnnotation(PagePrefix.class);
        if (annotation != null) {
            return annotation.value();
        }
        throw new IllegalStateException("Missing @PagePrefix annotation");
    }

    @GetMapping("")
    public String redirectToConsultaPath() {
        return "redirect:/pages/consulta/" + getPrefix() + "/";
    }

    @GetMapping("/{prefix}/")
    public abstract String list(Model model);

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