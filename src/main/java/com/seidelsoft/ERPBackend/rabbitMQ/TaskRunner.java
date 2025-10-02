package com.seidelsoft.ERPBackend.rabbitMQ;

import com.seidelsoft.ERPBackend.system.service.JsonConverter;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import com.seidelsoft.ERPBackend.taskManager.model.dto.PdfDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class TaskRunner implements CommandLineRunner {

    private final TaskProducer producer;
    private final JsonConverter jsonConverter;

    @Override
    public void run(String... args) throws Exception {
        String jsonEmail = jsonConverter.convertDtoToJson(getEmailDTO());
        String jsonPDF = jsonConverter.convertDtoToJson(getPdfDTO());

        producer.sendEmailTask(jsonEmail, 10);

        producer.sendPdfTask(jsonPDF, 2);

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

    private PdfDTO getPdfDTO() {
        Map<String, Object> dados = new HashMap<>();
        dados.put("asd", getEmailDTO());

        return PdfDTO.builder()
                .titulo("ASD")
                .cabecalhos(List.of("test", "tasd", "asd"))
                .dados(List.of(dados))
                .build();
    }
}
