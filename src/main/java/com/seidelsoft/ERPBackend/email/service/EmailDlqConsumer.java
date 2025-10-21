package com.seidelsoft.ERPBackend.email.service;

import com.seidelsoft.ERPBackend.email.dto.EmailDTO;
import com.seidelsoft.ERPBackend.rabbitMQ.DLQConsumer;
import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailDlqConsumer implements DLQConsumer<EmailDTO> {

    @Override
    @RabbitListener(queues = RabbitMQConfig.EMAIL_DLQ)
    public void consumir(EmailDTO email) {
        log.error("📥 Mensagem movida para DLQ de e-mails: {}", email);
        // Aqui você pode enviar o erro para Prometheus, Elastic, ou salvar num banco para auditoria
    }
}

