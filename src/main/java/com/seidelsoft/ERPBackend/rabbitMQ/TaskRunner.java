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
        producer.sendEmailTask("Email teste", 10);
        producer.sendEmailTask("Email teste", 5);
        producer.sendEmailTask("Email teste", 6);
        producer.sendEmailTask("Email teste", 7);
        producer.sendEmailTask("Email teste", 1);

        producer.sendPdfTask("PDF teste", 2);
        producer.sendPdfTask("PDF teste", 3);
        producer.sendPdfTask("PDF teste", 4);
        producer.sendPdfTask("PDF teste", 10);
        producer.sendPdfTask("PDF teste", 1);

        producer.sendReportTask("Relatório teste", 3);
    }
}
