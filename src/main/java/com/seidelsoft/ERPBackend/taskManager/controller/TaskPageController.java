package com.seidelsoft.ERPBackend.taskManager.controller;

import com.seidelsoft.ERPBackend.system.pages.BasePageController;
import com.seidelsoft.ERPBackend.taskManager.model.Task;
import com.seidelsoft.ERPBackend.taskManager.service.TaskService;
import com.seidelsoft.ERPBackend.taskManager.service.TaskTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pages/taskmanager")
public class TaskPageController extends BasePageController<Task, TaskService> {

    private final TaskTypeService taskTypeService;

    @GetMapping(path = "/execute/{id}")
    public String execute(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        Optional<Task> t = getService().findById(id);

        if (t.isPresent()) {
            getService().executeManually(t.get());
            redirectAttributes.addFlashAttribute("successMessage",
                    "Tarefa " + t.get().getName() + " executada com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "Tarefa não encontrada!");
        }
        return "redirect:"+getUrl()+"/consulta";
    }

    @Override
    public String update(long id, Task item) {
        super.update(id, item);
        Optional<Task> task = getService().getById(id);
        task.ifPresent(existingTask -> getService().updateTask(existingTask));
        return getUrlPageConsulta();
    }

    @Override
    public String showAddPage(Model model) {
        model.addAttribute("taskTypes", taskTypeService.findAll(Sort.by("name")));
        return super.showAddPage(model);
    }

    @Override
    public String showEditPage(long id, Model model) {
        model.addAttribute("taskTypes", taskTypeService.findAll(Sort.by("name")));
        return super.showEditPage(id, model);
    }

    @Override
    public TaskService getService() {
        return service;
    }

    @Override
    public String getListPageTitle() {
        return "Task Manager";
    }

    @Override
    public String getEditPageTitle() {
        return "Edit Task";
    }

    @Override
    public String getAddPageTitle() {
        return "Add Task";
    }

    @Override
    public String getUrl() {
        return "/pages/taskmanager";
    }

    @Override
    public Task getItem() {
        return new Task();
    }
}
