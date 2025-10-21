package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.email.service.EmailService;
import com.seidelsoft.ERPBackend.taskManager.model.entity.JobTask;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.support.ReferenceJobFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

@Service
@RequiredArgsConstructor
public class DynamicJobService {

    private final EmailService emailService;
    private final JobRegistry jobRegistry;
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    public void registerJob(String jobName, JobTask task) throws Exception {

        Step step = new StepBuilder(jobName + "Step", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    task.execute();
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();

        Job job = new JobBuilder(jobName, jobRepository)
                .listener(new TaskCompletionListener(emailService))
                .start(step)
                .build();

        jobRegistry.register(new ReferenceJobFactory(job));

        System.out.println("Job registrado dinamicamente: " + jobName);
    }
}
