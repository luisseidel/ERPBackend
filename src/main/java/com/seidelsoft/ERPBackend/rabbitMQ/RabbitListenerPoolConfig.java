package com.seidelsoft.ERPBackend.rabbitMQ;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

@Configuration
public class RabbitListenerPoolConfig {

    private SimpleRabbitListenerContainerFactory createFactory(ConnectionFactory cf, int min, int max) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(cf);
        factory.setConcurrentConsumers(min);
        factory.setMaxConcurrentConsumers(max);

        // Retry template
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(3);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(1000);
        backOffPolicy.setMultiplier(2);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);

        factory.setRetryTemplate(retryTemplate);
        return factory;
    }

    @Bean(name = "emailListenerFactory")
    public SimpleRabbitListenerContainerFactory emailListenerFactory(ConnectionFactory cf) {
        return createFactory(cf, 2, 5);
    }

    @Bean(name = "pdfListenerFactory")
    public SimpleRabbitListenerContainerFactory pdfListenerFactory(ConnectionFactory cf) {
        return createFactory(cf, 1, 3);
    }

    @Bean(name = "reportListenerFactory")
    public SimpleRabbitListenerContainerFactory reportListenerFactory(ConnectionFactory cf) {
        return createFactory(cf, 1, 2);
    }
}
