package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.taskManager.model.Task;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import org.springframework.stereotype.Component;

@Component
public class SendMailTask implements BaseTask {

    @Override
    public void execute(Task task) {
        System.out.println("Enviando email com parâmetros: " + task.getName());
    }
}
