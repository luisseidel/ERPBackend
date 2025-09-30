package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;

public abstract class BaseTask {

    public abstract TaskTypeEnum getTaskType();

    void prepareExecution(Task task) {
        System.out.println("Executando task: " + task.getName());
        execute();
    }

    public void execute() {}

}
