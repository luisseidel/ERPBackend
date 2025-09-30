package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SendMailTask extends BaseTask {

    private final JavaMailSender mailSender;

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.EMAIL;
    }

    @Override
    public void execute() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("seu_email@gmail.com");
        message.setTo("asd@asd.com");
        message.setSubject("test");
        message.setText("test");

        mailSender.send(message);
    }
}
