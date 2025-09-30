package com.seidelsoft.ERPBackend.rabbitMQ;

import com.seidelsoft.ERPBackend.system.service.JsonConverter;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import com.seidelsoft.ERPBackend.taskManager.tasks.SendEmailTask;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskMetrics metrics;
    private final SendEmailTask sendEmailTask;
    private final JsonConverter jsonConverter;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE, containerFactory = "emailListenerFactory")
    public void handleEmail(String payload) {
        try {
            System.out.println("Processando email: " + payload);
            EmailDTO dto = jsonConverter.convertJsonToDto(payload, EmailDTO.class);

            sendEmailTask.setEmailDTO(dto);
            sendEmailTask.execute();

            metrics.incrementEmail();
        } catch (Exception e) {
            log.error("não foi possível enviar o email: " + e);
        }
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_QUEUE, containerFactory = "pdfListenerFactory")
    public void handlePdf(String payload) {
        System.out.println("Gerando PDF: " + payload);


        metrics.incrementPdf();
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_QUEUE, containerFactory = "reportListenerFactory")
    public void handleReport(String payload) {
        System.out.println("Gerando relatório: " + payload);
        metrics.incrementReport();
    }

    // DLQ
    @RabbitListener(queues = RabbitMQConfig.EMAIL_DLQ)
    public void handleEmailDLQ(String payload) {
        System.err.println("Falha email: " + payload);
        metrics.incrementEmailFail();
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_DLQ)
    public void handlePdfDLQ(String payload) {
        System.err.println("Falha PDF: " + payload);
        metrics.incrementPdfFail();
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_DLQ)
    public void handleReportDLQ(String payload) {
        System.err.println("Falha relatório: " + payload);
        metrics.incrementReportFail();
    }

}
