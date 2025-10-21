package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.rabbitMQ.DLQConsumer;
import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskDlqConsumer implements DLQConsumer<Task> {

    @Override
    @RabbitListener(queues = RabbitMQConfig.GENERAL_DLQ)
    public void consumir(Task task) {
        log.error("📥 Mensagem movida para DLQ de e-mails: {}", task.getName());

    }
}
