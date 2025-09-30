package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GeneratePDFTask extends BaseTask {

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.PDF;
    }

    @Override
    public void execute() {
        try {
            System.out.println("Gerando PDF...");
        } catch (Exception e) {
        }
    }
}
