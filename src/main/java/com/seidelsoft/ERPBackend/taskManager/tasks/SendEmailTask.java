package com.seidelsoft.ERPBackend.taskManager.tasks;

import com.seidelsoft.ERPBackend.taskManager.model.TaskTypeEnum;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import com.seidelsoft.ERPBackend.taskManager.service.BaseTask;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Slf4j
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
        try {
            if (emailDTO != null) {
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
                helper.setTo(emailDTO.from());

                helper.setSubject(emailDTO.subject());
                helper.setText(emailDTO.body(), true);

                mailSender.send(mimeMessage);
            }
        } catch (Exception e) {
            log.error("Erro ao enviar mensagem: ", e);
        }
    }
}
