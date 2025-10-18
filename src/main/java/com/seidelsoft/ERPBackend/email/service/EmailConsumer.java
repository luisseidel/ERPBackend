package com.seidelsoft.ERPBackend.email.service;

import com.seidelsoft.ERPBackend.email.dto.EmailDTO;
import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailConsumer {

    private final JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void consumirMensagem(EmailDTO email) {
        log.info("📩 Consumindo mensagem de e-mail para: {}", email.destination());
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(email.from());
            message.setTo(email.destination());
            message.setSubject(email.subject());
            message.setText(email.body());
            mailSender.send(message);

            log.info("✅ E-mail enviado com sucesso para: {}", email.destination());
        } catch (Exception e) {
            log.error("❌ Erro ao enviar e-mail para {}: {}", email.destination(), e.getMessage());
        }
    }
}
