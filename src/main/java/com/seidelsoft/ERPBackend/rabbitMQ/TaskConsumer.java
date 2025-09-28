package com.seidelsoft.ERPBackend.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {

    @RabbitListener(queues = RabbitMQConfig.EMAIL_QUEUE)
    public void handleEmail(String payload) {
        System.out.println("Processando email: " + payload);
        // Lógica de envio de email
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_QUEUE)
    public void handlePdf(String payload) {
        System.out.println("Gerando PDF: " + payload);
        // Lógica de geração de PDF
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_QUEUE)
    public void handleReport(String payload) {
        System.out.println("Gerando relatório: " + payload);
        // Lógica de relatório
    }

    @RabbitListener(queues = RabbitMQConfig.EMAIL_DLQ)
    public void handleEmailDLQ(String payload) {
        System.err.println("Falha no processamento do email: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.PDF_DLQ)
    public void handlePdfDLQ(String payload) {
        System.err.println("Falha no processamento do PDF: " + payload);
    }

    @RabbitListener(queues = RabbitMQConfig.REPORT_DLQ)
    public void handleReportDLQ(String payload) {
        System.err.println("Falha no processamento do relatório: " + payload);
    }

}
