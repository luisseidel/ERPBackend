package com.seidelsoft.ERPBackend.taskManager.controller;

import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import com.seidelsoft.ERPBackend.taskManager.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController extends BaseRestController<Task, TaskService> {

    @Override
    @PostMapping
    public ResponseEntity create(@Valid @RequestBody Task dto) throws ValidacaoException {
        Task task = getService().save(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(task.getId())
                .toUri();

        return ResponseEntity.created(location).body(task);
    }

    @Override
    @PutMapping
    public ResponseEntity update(@Valid @RequestBody Task dto) throws ValidacaoException {
        return ResponseEntity.ok(getService().save(dto));
    }
}
