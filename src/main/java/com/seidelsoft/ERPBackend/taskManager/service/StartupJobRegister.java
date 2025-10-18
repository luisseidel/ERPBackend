package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.taskManager.model.entity.JobTask;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import com.seidelsoft.ERPBackend.taskManager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StartupJobRegister {

    private final TaskRepository taskRepository;
    private final DynamicJobService dynamicJobService;
    private final List<JobTask> jobTasks;

    @PostConstruct
    public void init() throws Exception {
        Map<String, JobTask> jobTaskMap = jobTasks.stream()
                .collect(Collectors.toMap(JobTask::getJobName, Function.identity()));

        List<Task> tasksParaRegistrar = taskRepository.findByActiveTrueAndRegisterJobTrue();

        for (Task task : tasksParaRegistrar) {
            JobTask jobTask = jobTaskMap.get(task.getName());

            if (jobTask == null) {
                throw new IllegalArgumentException("Não foi encontrada implementação para Task: " + task.getName());
            }

            dynamicJobService.registerJob(task.getName(), jobTask);
            System.out.println("Job registrado dinamicamente: " + task.getName());
        }
    }
}
