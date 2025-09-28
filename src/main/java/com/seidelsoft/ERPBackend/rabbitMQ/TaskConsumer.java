package com.seidelsoft.ERPBackend.rabbitMQ;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskConsumer {

    private final TaskMetrics metrics;

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE, containerFactory = "emailListenerFactory")
    public void handleEmail(String payload) {
        System.out.println("Processando email: " + payload);
        metrics.incrementEmail();
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
