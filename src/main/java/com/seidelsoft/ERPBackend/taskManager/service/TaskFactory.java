package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TaskFactory {

    private final Map<TaskTypeEnum, BaseTask> tasks;

    // O Spring irá injetar automaticamente uma lista de todos os beans que estendem BaseTask
    public TaskFactory(List<BaseTask> taskImplementations) {
        // Usamos um EnumMap para performance e segurança de tipos
        tasks = new EnumMap<>(TaskTypeEnum.class);
        // Populamos o mapa usando o tipo que cada tarefa agora nos fornece
        for (BaseTask task : taskImplementations) {
            tasks.put(task.getTaskType(), task);
        }
    }

    public BaseTask getTask(Task task) {
        TaskTypeEnum type = TaskTypeEnum.valueOf(task.getTaskType());
        if (type == null) {
            throw new IllegalArgumentException("Tipo de tarefa inválido ou nulo: " + task.getTaskType());
        }

        return Optional.ofNullable(tasks.get(type))
                .orElseThrow(() -> new IllegalArgumentException("Nenhuma implementação encontrada para TaskType: " + type.getDescription()));
    }
}
