package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendTask(String taskType, String payload) {
        TaskMessage taskMessage = new TaskMessage(taskType, payload);
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "task-routing-key", taskMessage);
    }

}
