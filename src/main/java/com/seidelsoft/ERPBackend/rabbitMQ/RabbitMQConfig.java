package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitMQConfig {

    // Exchanges
    public static final String TASK_EXCHANGE = "task-exchange";
    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";

    // Filas principais
    public static final String EMAIL_QUEUE = "email-queue";
    public static final String REPORT_QUEUE = "report-queue";
    public static final String GENERAL_QUEUE = "general-queue";

    // Filas principais
    public static final String EMAIL_ROUTING_KEY = "email-routing-key";
    public static final String REPORT_ROUTING_KEY = "report-routing-key";
    public static final String GENERAL_ROUTING_KEY = "general-routing-key";

    // Dead Letter Queues
    public static final String EMAIL_DLQ = "email-dlq";
    public static final String REPORT_DLQ = "report-dlq";
    public static final String GENERAL_DLQ = "general-dlq";

    // === DEAD LETTER ROUTING KEYS ===
    public static final String EMAIL_DLQ_ROUTING_KEY = "email-dlq-routing-key";
    public static final String REPORT_DLQ_ROUTING_KEY = "report-dlq-routing-key";
    public static final String GENERAL_DLQ_ROUTING_KEY = "general-dlq-routing-key";

    @Bean
    public DirectExchange taskExchange() {
        log.info("Registrando exchange principal: {}", TASK_EXCHANGE);
        return new DirectExchange(TASK_EXCHANGE);
    }

    @Bean
    public DirectExchange deadLetterExchange() {
        log.info("Registrando Dead Letter Exchange: {}", DEAD_LETTER_EXCHANGE);
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue emailQueue() {
        return QueueBuilder.durable(EMAIL_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", EMAIL_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue reportQueue() {
        return QueueBuilder.durable(REPORT_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", REPORT_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public Queue generalQueue() {
        return QueueBuilder.durable(GENERAL_QUEUE)
                .withArgument("x-max-priority", 10)  // prioridade de 0 a 10
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE)
                .withArgument("x-dead-letter-routing-key", GENERAL_DLQ_ROUTING_KEY)
                .build();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    // Dead Letter Queues
    @Bean
    public Queue emailDLQ() { return QueueBuilder.durable(EMAIL_DLQ).build(); }
    @Bean
    public Queue generalDLQ() { return QueueBuilder.durable(GENERAL_DLQ).build(); }
    @Bean
    public Queue reportDLQ() { return QueueBuilder.durable(REPORT_DLQ).build(); }

    // Bindings
    @Bean
    public Binding emailBinding() {
        log.info("Ligando fila '{}' ao exchange '{}' com routing key '{}'",
                EMAIL_QUEUE, TASK_EXCHANGE, EMAIL_ROUTING_KEY);
        return BindingBuilder.bind(emailQueue()).to(taskExchange()).with(EMAIL_ROUTING_KEY);
    }

    @Bean
    public Binding generalBinding() {
        log.info("Ligando fila '{}' ao exchange '{}' com routing key '{}'",
                GENERAL_QUEUE, TASK_EXCHANGE, GENERAL_ROUTING_KEY);
        return BindingBuilder.bind(generalQueue()).to(taskExchange()).with(GENERAL_ROUTING_KEY);
    }

    @Bean
    public Binding reportBinding() {
        log.info("Ligando fila '{}' ao exchange '{}' com routing key '{}'",
                REPORT_QUEUE, TASK_EXCHANGE, REPORT_ROUTING_KEY);
        return BindingBuilder.bind(reportQueue()).to(taskExchange()).with(REPORT_ROUTING_KEY);
    }

    @Bean
    public Binding emailDLQBinding() {
        return BindingBuilder.bind(emailDLQ()).to(deadLetterExchange()).with(EMAIL_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding reportDLQBinding() {
        return BindingBuilder.bind(reportDLQ()).to(deadLetterExchange()).with(REPORT_DLQ_ROUTING_KEY);
    }

    @Bean
    public Binding generalDLQBinding() {
        return BindingBuilder.bind(generalDLQ()).to(deadLetterExchange()).with(GENERAL_DLQ_ROUTING_KEY);
    }

}
