package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.rabbitMQ.TaskMetrics;
import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExecuteReportTask extends BaseTask {

    private final TaskMetrics metrics;

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.REPORT;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Criando report!");
        } catch (Exception e) {
        }
    }
}
