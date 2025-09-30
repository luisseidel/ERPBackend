package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.taskManager.model.Task;
import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;

public abstract class BaseTask {

    public abstract TaskTypeEnum getTaskType();

    void prepareExecution(Task task) {
        System.out.println("Executando task: " + task.getName());
        execute();
    }

    public void execute() {}

}
