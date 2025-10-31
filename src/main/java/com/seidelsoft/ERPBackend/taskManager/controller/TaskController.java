package com.seidelsoft.ERPBackend.taskManager.controller;

import com.seidelsoft.ERPBackend.system.controller.BaseRestController;
import com.seidelsoft.ERPBackend.system.exception.ValidacaoException;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import com.seidelsoft.ERPBackend.taskManager.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/task")
public class TaskController extends BaseRestController<Task, TaskService> {
    @Override
    public ResponseEntity list(int page, int size) {
        return null;
    }

    @Override
    public ResponseEntity get(Long id) {
        return null;
    }

    @Override
    public ResponseEntity create(Task dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity update(Task dto) throws ValidacaoException {
        return null;
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        return null;
    }
}
