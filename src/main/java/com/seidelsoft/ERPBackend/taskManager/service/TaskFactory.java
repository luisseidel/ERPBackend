package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.taskManager.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskFactory {

    private final Map<String, BaseTask> tasks;

    public BaseTask getTask(Task task) {
        String tipo = task.getTaskType().getName().toUpperCase();
        return tasks.entrySet().stream()
                .filter(entry -> entry.getKey().toUpperCase().equals(tipo))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma implementação encontrada para TaskType: " + tipo));
    }

}
