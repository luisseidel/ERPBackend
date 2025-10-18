package com.seidelsoft.ERPBackend.rabbitMQ;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class TaskMetrics {

    private final Counter emailCounter;
    private final Counter generalTaskCounter;
    private final Counter reportCounter;

    private final Counter emailFailCounter;
    private final Counter generalTaskFailCounter;
    private final Counter reportFailCounter;

    public TaskMetrics(MeterRegistry registry) {
        this.emailCounter = Counter.builder("tasks.email.processed")
                .description("Quantidade de emails processados")
                .register(registry);
        this.generalTaskCounter = Counter.builder("tasks.general.processed")
                .description("Quantidade de Tasks gerais processados")
                .register(registry);
        this.reportCounter = Counter.builder("tasks.report.processed")
                .description("Quantidade de relatórios processados")
                .register(registry);

        this.emailFailCounter = Counter.builder("tasks.email.fail")
                .description("Quantidade de e-mails com falha")
                .register(registry);
        this.generalTaskFailCounter = Counter.builder("tasks.general.fail")
                .description("Quantidade de Task gerais com falha")
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

    public void incrementGeneralTask() {
        generalTaskCounter.increment();
    }

    public void incrementGeneralFail() {
        generalTaskFailCounter.increment();
    }

    public void incrementReport() {
        reportCounter.increment();
    }

    public void incrementReportFail() {
        reportFailCounter.increment();
    }
}
