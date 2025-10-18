package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskPublisher {

    private final AmqpTemplate amqpTemplate;

    public void enfileirarTask(Task task) {
        amqpTemplate.convertAndSend(
                RabbitMQConfig.GENERAL_QUEUE,
                RabbitMQConfig.GENERAL_DLQ,
                task
        );
        log.info("📤 Tarefa enfileirada para execução: {}", task.getName());
    }
}