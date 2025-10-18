package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.email.dto.EmailDTO;
import com.seidelsoft.ERPBackend.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
@RequiredArgsConstructor
public class TaskCompletionListener implements JobExecutionListener {

    private final EmailService emailService;

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("⏳ Iniciando job: {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        BatchStatus status = jobExecution.getStatus();

        if (status == BatchStatus.COMPLETED) {
            log.info("✅ Job finalizado com sucesso: {}", jobName);
            enviarEmailConclusao(jobName, true);
        } else if (status == BatchStatus.FAILED) {
            log.error("❌ Job falhou: {}", jobName);
            enviarEmailConclusao(jobName, false);
        }
    }

    private void enviarEmailConclusao(String jobName, boolean sucesso) {
        String assunto = sucesso
                ? "Job concluído com sucesso"
                : "Job falhou durante execução";

        String corpo = sucesso
                ? "O job '" + jobName + "' foi concluído com sucesso."
                : "O job '" + jobName + "' encontrou erros durante sua execução.";

        emailService.enfileirarEmail(
                EmailDTO.builder()
                        .from("no-reply@sistema.com")
                        .destination("admin@sistema.com")
                        .subject(assunto)
                        .body(corpo)
                        .build()
        );
    }

}
