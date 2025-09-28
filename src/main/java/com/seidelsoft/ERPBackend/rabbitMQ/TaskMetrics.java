package com.seidelsoft.ERPBackend.rabbitMQ;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TaskMetrics {

    private final Counter emailCounter;
    private final Counter pdfCounter;
    private final Counter reportCounter;

    private final Counter emailFailCounter;
    private final Counter pdfFailCounter;
    private final Counter reportFailCounter;

    public TaskMetrics(MeterRegistry registry) {
        this.emailCounter = Counter.builder("tasks.email.processed")
                .description("Quantidade de emails processados")
                .register(registry);
        this.pdfCounter = Counter.builder("tasks.pdf.processed")
                .description("Quantidade de PDFs processados")
                .register(registry);
        this.reportCounter = Counter.builder("tasks.report.processed")
                .description("Quantidade de relatórios processados")
                .register(registry);

        this.emailFailCounter = Counter.builder("tasks.email.fail")
                .description("Quantidade de e-mails com falha")
                .register(registry);
        this.pdfFailCounter = Counter.builder("tasks.pdf.fail")
                .description("Quantidade de PDFs com falha")
                .register(registry);
        this.reportFailCounter = Counter.builder("tasks.report.fail")
                .description("Quantidade de reports processados")
                .register(registry);
    }

    public void incrementEmail() {
        emailCounter.increment();
    }

    public void incrementEmailFail() {
        emailFailCounter.increment();
    }

    public void incrementPdf() {
        pdfCounter.increment();
    }

    public void incrementPdfFail() {
        pdfFailCounter.increment();
    }

    public void incrementReport() {
        reportCounter.increment();
    }

    public void incrementReportFail() {
        reportFailCounter.increment();
    }
}
