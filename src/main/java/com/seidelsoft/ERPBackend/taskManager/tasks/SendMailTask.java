package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.rabbitMQ.TaskMetrics;
import com.seidelsoft.ERPBackend.taskManager.model.Task;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMailTask implements BaseTask {

    private final TaskMetrics metrics;

    @Override
    public void execute(Task task) {
        System.out.println("Enviando email com parâmetros: " + task.getName());
        metrics.incrementEmail();
    }
}
