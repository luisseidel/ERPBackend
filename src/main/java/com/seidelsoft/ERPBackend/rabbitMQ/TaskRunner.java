package com.seidelsoft.ERPBackend.rabbitMQ;

import com.seidelsoft.ERPBackend.system.service.JsonConverter;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskRunner implements CommandLineRunner {

    private final TaskProducer producer;
    private final JsonConverter jsonConverter;

    @Override
    public void run(String... args) throws Exception {
        String jsonEmail = jsonConverter.convertDtoToJson(getEmailDTO());

        producer.sendEmailTask(jsonEmail, 10);
        producer.sendEmailTask(jsonEmail, 5);
        producer.sendEmailTask(jsonEmail, 6);
        producer.sendEmailTask(jsonEmail, 7);
        producer.sendEmailTask(jsonEmail, 1);

        producer.sendPdfTask("PDF teste", 2);
        producer.sendPdfTask("PDF teste", 3);
        producer.sendPdfTask("PDF teste", 4);
        producer.sendPdfTask("PDF teste", 10);
        producer.sendPdfTask("PDF teste", 1);

        producer.sendReportTask("Relatório teste", 3);
    }

    private EmailDTO getEmailDTO() {
        return EmailDTO.builder()
                .from("asd@asd.com")
                .destination("asd@asd.com")
                .subject("Subject")
                .body("body")
                .build();
    }
}
