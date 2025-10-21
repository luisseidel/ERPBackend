package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.rabbitMQ.QueueConsumer;
import com.seidelsoft.ERPBackend.rabbitMQ.RabbitMQConfig;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskConsumer implements QueueConsumer<Task> {

    private final JobLauncher jobLauncher;
    private final JobLocator jobLocator;

    @Override
    @RabbitListener(queues = RabbitMQConfig.GENERAL_QUEUE)
    public void consumir(Task task) {
        log.info("📥 Recebida task da fila: {}", task.getName());

        try {
            Job job = jobLocator.getJob(task.getName());
            JobParameters params = new JobParametersBuilder()
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            jobLauncher.run(job, params);
            log.info("🚀 Job iniciado: {}", task.getName());

        } catch (Exception e) {
            log.error("❌ Erro ao executar job: {}", task.getName(), e);
        }
    }
}
