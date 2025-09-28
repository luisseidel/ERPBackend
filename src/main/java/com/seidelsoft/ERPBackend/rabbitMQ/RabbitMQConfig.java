package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    // Filas principais
    public static final String EMAIL_QUEUE = "email-queue";
    public static final String PDF_QUEUE = "pdf-queue";
    public static final String REPORT_QUEUE = "report-queue";

    // Dead Letter Queues
    public static final String EMAIL_DLQ = "email-dlq";
    public static final String PDF_DLQ = "pdf-dlq";
    public static final String REPORT_DLQ = "report-dlq";

    // Exchanges
    public static final String TASK_EXCHANGE = "task-exchange";

    @Bean
    public DirectExchange taskExchange() {
        return new DirectExchange(TASK_EXCHANGE);
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", TASK_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", EMAIL_DLQ)
                .build();
    }

    @Bean
    public Queue pdfQueue() {
        return QueueBuilder.durable(PDF_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", TASK_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", PDF_DLQ)
                .build();
    }

    @Bean
    public Queue reportQueue() {
        return QueueBuilder.durable(REPORT_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", TASK_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", REPORT_DLQ)
                .build();
    }

    // Dead Letter Queues
    @Bean
    public Queue emailDLQ() { return QueueBuilder.durable(EMAIL_DLQ).build(); }
    @Bean
    public Queue pdfDLQ() { return QueueBuilder.durable(PDF_DLQ).build(); }
    @Bean
    public Queue reportDLQ() { return QueueBuilder.durable(REPORT_DLQ).build(); }

    // Bindings
    @Bean
    public Binding emailBinding() {
        log.info("Ligando fila email");
        return BindingBuilder.bind(emailQueue()).to(taskExchange()).with(EMAIL_QUEUE);
    }

    @Bean
    public Binding pdfBinding() {
        log.info("Ligando fila pdf");
        return BindingBuilder.bind(pdfQueue()).to(taskExchange()).with(PDF_QUEUE);
    }

    @Bean
    public Binding reportBinding() {
        log.info("Ligando fila report");
        return BindingBuilder.bind(reportQueue()).to(taskExchange()).with(REPORT_QUEUE);
    }

    @Bean
    public Binding emailDLQBinding() {
        log.info("Ligando DLQ email");
        return BindingBuilder.bind(emailDLQ()).to(taskExchange()).with(EMAIL_DLQ);
    }

    @Bean
    public Binding pdfDLQBinding() {
        log.info("Ligando DLQ pdf");
        return BindingBuilder.bind(pdfDLQ()).to(taskExchange()).with(PDF_DLQ);
    }

    @Bean
    public Binding reportDLQBinding() {
        log.info("Ligando DLQ report");
        return BindingBuilder.bind(reportDLQ()).to(taskExchange()).with(REPORT_DLQ);
    }

}
