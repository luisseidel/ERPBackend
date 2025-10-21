package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;


    public void sendTask(String queue, String payload, int priority) {
        Message message = MessageBuilder.withBody(payload.getBytes())
                .setPriority(priority)
                .build();
        rabbitTemplate.send(RabbitMQConfig.TASK_EXCHANGE, queue, message);
    }


    public void sendEmailTask(String payload, int priority) {
        sendTask(RabbitMQConfig.EMAIL_ROUTING_KEY, payload, priority);
    }

    public void sendPdfTask(String payload, int priority) {
        sendTask(RabbitMQConfig.GENERAL_ROUTING_KEY, payload, priority);
    }

    public void sendReportTask(String payload, int priority) {
        sendTask(RabbitMQConfig.REPORT_ROUTING_KEY, payload, priority);
    }

}
