package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.system.service.BaseService;
import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import com.seidelsoft.ERPBackend.taskManager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService extends BaseService<Task, TaskRepository> {

    private final TaskFactory taskFactory;
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    private final Map<Long, ScheduledFuture<?>> scheduledTasks = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSchedule() {
        taskScheduler.setPoolSize(5);
        taskScheduler.setThreadNamePrefix("dynamic-task-");
        taskScheduler.initialize();

        List<Task> list = getSpecificRepository().findByActiveTrue();
        list.forEach(this::scheduleTask);
    }

    public void rescheduleTask(Task task) {
        cancelTask(task.getId());
        scheduleTask(task);
    }

    @Override
    public void afterSave(Task savedItem) {
        if (savedItem.getActive()) {
            rescheduleTask(savedItem);
        } else {
            cancelTask(savedItem.getId());
        }
    }

    private void scheduleTask(Task task) {
        cancelTask(task.getId());

        ScheduledFuture<?> future = taskScheduler.schedule(
                () -> executeTask(task),
                new CronTrigger(task.getCronExpression())
        );

        scheduledTasks.put(task.getId(), future);
    }

    public void executeTask(Task task) {
        if (task.getActive()) {
            log.info("Tarefa " + task.getName() + " executada!");
            BaseTask btask = taskFactory.getTask(task);
            btask.prepareExecution(task);
        }
    }

    public void executeManually(Task task) {
        if (task.getActive()) {
            log.info("Task Executada manualmente");
            BaseTask btask = taskFactory.getTask(task);
            btask.prepareExecution(task);
        }
    }

    public void cancelTask(Long id) {
        ScheduledFuture<?> future = scheduledTasks.get(id);
        if (future != null) {
            future.cancel(false);
            scheduledTasks.remove(id);
            System.out.println("Agendamento cancelado para ID: " + id);
        }
    }

    public Optional<Task> findById(Long id) {
        return getSpecificRepository().findById(id);
    }

}
