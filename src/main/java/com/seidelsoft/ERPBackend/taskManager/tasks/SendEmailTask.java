package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class SendEmailTask extends BaseTask {

    private final JavaMailSender mailSender;
    private EmailDTO emailDTO;

    @Override
    public TaskTypeEnum getTaskType() {
        return TaskTypeEnum.EMAIL;
    }

    @Override
    public void execute() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(emailDTO.from());
        message.setTo(emailDTO.destination());
        message.setSubject(emailDTO.subject());
        message.setText(emailDTO.body());

        mailSender.send(message);
    }
}
