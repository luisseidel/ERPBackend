package com.seidelsoft.ERPBackend.rabbitMQ;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class TaskConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveTask(TaskMessage taskMessage) {
        System.out.println("Received task: " + taskMessage.taskType() + " with payload: " + taskMessage.payload());
        switch (taskMessage.taskType()) {
            case "EMAIL" -> sendEmail(taskMessage.payload());
            case "PDF" -> generatePDF(taskMessage.payload());
            case "REPORT" -> generateReport(taskMessage.payload());
            default -> System.out.println("Unkown task: " + taskMessage.taskType());
        }
    }


    private void sendEmail(String payload) {
        // Simulação
        System.out.println("Enviando email: " + payload);
        // Aqui você integraria com JavaMailSender
    }

    private void generatePDF(String payload) {
        System.out.println("Gerando PDF: " + payload);
        // Aqui você integraria com iText
    }

    private void generateReport(String payload) {
        System.out.println("Gerando relatório: " + payload);
        // Aqui você faria a lógica de relatório
    }

}
