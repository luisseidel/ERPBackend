package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendEmailTask(String payload) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_EXCHANGE, RabbitMQConfig.EMAIL_QUEUE, payload);
    }

    public void sendPdfTask(String payload) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_EXCHANGE, RabbitMQConfig.PDF_QUEUE, payload);
    }

    public void sendReportTask(String payload) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.TASK_EXCHANGE, RabbitMQConfig.REPORT_QUEUE, payload);
    }

}
