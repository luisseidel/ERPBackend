package com.seidelsoft.ERPBackend.email.service;

import com.seidelsoft.ERPBackend.email.dto.EmailDTO;
import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final AmqpTemplate amqpTemplate;

    public void enfileirarEmail(EmailDTO email) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.TASK_EXCHANGE,
                RabbitMQConfig.EMAIL_ROUTING_KEY,
                email
        );
        log.info("📤 Mensagem de e-mail enfileirada para: {}", email.destination());
    }
}
