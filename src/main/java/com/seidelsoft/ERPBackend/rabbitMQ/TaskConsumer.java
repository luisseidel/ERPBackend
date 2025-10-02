package com.seidelsoft.ERPBackend.rabbitMQ;

import com.seidelsoft.ERPBackend.pdfGenerator.dto.PdfPayloadDTO;
import com.seidelsoft.ERPBackend.system.service.JsonConverter;
import com.seidelsoft.ERPBackend.taskManager.model.dto.EmailDTO;
import com.seidelsoft.ERPBackend.taskManager.tasks.GeneratePDFTask;
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
    private final GeneratePDFTask generatePDFTask;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE, containerFactory = "emailListenerFactory")
    public void handleEmail(String payload) {
        log.info("Processando email: " + payload);
        EmailDTO dto = jsonConverter.convertJsonToDto(payload, EmailDTO.class);
        sendEmailTask.setEmailDTO(dto);
        sendEmailTask.execute();
        metrics.incrementEmail();
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_QUEUE, containerFactory = "pdfListenerFactory")
    public void handlePdf(String payload) {
        log.info("Gerando PDF: " + payload);
        PdfPayloadDTO pdfPayload = jsonConverter.convertJsonToDto(payload, PdfPayloadDTO.class);
        generatePDFTask.setPdfPayloadDTO(pdfPayload);
        generatePDFTask.execute();
        metrics.incrementPdf();
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_QUEUE, containerFactory = "reportListenerFactory")
    public void handleReport(String payload) {
        log.info("Gerando relatório: " + payload);
        metrics.incrementReport();
    }

    // DLQ
    @RabbitListener(queues = RabbitMQConfig.EMAIL_DLQ)
    public void handleEmailDLQ(String payload) {
        log.error("Falha email: " + payload);
        metrics.incrementEmailFail();
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_DLQ)
    public void handlePdfDLQ(String payload) {
        log.error("Falha PDF: " + payload);
        metrics.incrementPdfFail();
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_DLQ)
    public void handleReportDLQ(String payload) {
        log.error("Falha relatório: " + payload);
        metrics.incrementReportFail();
    }

}
