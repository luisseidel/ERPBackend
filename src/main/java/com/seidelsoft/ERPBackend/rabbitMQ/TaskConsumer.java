package com.seidelsoft.ERPBackend.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE, containerFactory = "emailListenerFactory")
    public void handleEmail(String payload) {
        System.out.println("Processando email: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_QUEUE, containerFactory = "pdfListenerFactory")
    public void handlePdf(String payload) {
        System.out.println("Gerando PDF: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_QUEUE, containerFactory = "reportListenerFactory")
    public void handleReport(String payload) {
        System.out.println("Gerando relatório: " + payload);
    }

    // DLQ
    @RabbitListener(queues = RabbitMQConfig.EMAIL_DLQ)
    public void handleEmailDLQ(String payload) {
        System.err.println("Falha email: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_DLQ)
    public void handlePdfDLQ(String payload) {
        System.err.println("Falha PDF: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_DLQ)
    public void handleReportDLQ(String payload) {
        System.err.println("Falha relatório: " + payload);
    }

}
