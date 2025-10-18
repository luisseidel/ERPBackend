package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.system.service.BaseService;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import com.seidelsoft.ERPBackend.taskManager.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService extends BaseService<Task, TaskRepository> {

    private final TaskRepository taskRepository;
    private final TaskPublisher taskPublisher;

    @Scheduled(cron = "0 */10 * * * *")
    public void verificarTarefas() {
        LocalDateTime agora = LocalDateTime.now();
        List<Task> tasks = taskRepository.findByActiveTrue();

        for (Task task : tasks) {
            if (CronExpression.parse(task.getCronExpression()).next(agora.minusMinutes(1))
                    .isBefore(agora.plusSeconds(10))) {
                taskPublisher.enfileirarTask(task);
            }
        }
    }

    public void executeManually(Task task) {
        if (task.getActive()) {
            log.info("⏩ Execução manual requisitada para: {}", task.getName());
            taskPublisher.enfileirarTask(task);
        }
    }

    public Optional<Task> findById(Long id) {
        return getSpecificRepository().findById(id);
    }

    @Override
    public void beforeSave(Task item) {
        if (item.getRegisterJob() == null) {
            item.setRegisterJob(Boolean.FALSE);
        }
    }
}
