package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.system.service.BaseService;
import com.seidelsoft.ERPBackend.taskManager.model.Task;
import com.seidelsoft.ERPBackend.taskManager.repository.TaskRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService extends BaseService<Task, TaskRepository> {

    private final TaskScheduler scheduler;
    private final TaskFactory taskFactory;
    private final Map<Long, ScheduledFuture<?>> tarefasAgendadas = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSchedule() {
        List<Task> list = getSpecificRepository().findByActiveTrue();
        list.forEach(this::scheduleTask);
    }

    public void updateTask(Task task) {
        cancelTask(task.getId());
        scheduleTask(task);
    }

    private void scheduleTask(Task task) {
        if (!task.getActive()) return;

        CronTrigger cronTrigger = new CronTrigger(task.getCronExpression(), ZoneId.systemDefault());
        log.info("Tarefa " + task.getName() + " agendada!");
        ScheduledFuture<?> future = scheduler.schedule(
                () -> executeScheduleTask(task), cronTrigger
        );

        tarefasAgendadas.put(task.getId(), future);
    }

    public void executeScheduleTask(Task task) {
        if (task.getActive()) {
            log.info("Tarefa " + task.getName() + " executada!");
            BaseTask btask = taskFactory.getTask(task);
            btask.execute(task);
        }
    }


    public void executeManually(Task task) {
        if (task.getActive()) {
            log.info("Task Executada manualmente");
            BaseTask btask = taskFactory.getTask(task);
            btask.execute(task);
        }
    }

    public void cancelTask(Long id) {
        ScheduledFuture<?> future = tarefasAgendadas.get(id);
        if (future != null) {
            future.cancel(false);
            tarefasAgendadas.remove(id);
            System.out.println("Agendamento cancelado para ID: " + id);
        }
    }

    public Optional<Task> findById(Long id) {
        return getSpecificRepository().findById(id);
    }

}
