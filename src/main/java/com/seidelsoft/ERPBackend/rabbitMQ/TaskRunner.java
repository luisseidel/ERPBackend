package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRunner implements CommandLineRunner {

    private final TaskProducer producer;

    @Override
    public void run(String... args) throws Exception {
        producer.sendTask("EMAIL", "Email teste");
        producer.sendTask("PDF", "PDF teste");
        producer.sendTask("REPORT", "Relatório teste");
    }
}
