package com.seidelsoft.ERPBackend.taskManager.repository;

import com.seidelsoft.ERPBackend.taskManager.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByActiveTrue();
    List<Task> findByActiveTrueAndRegisterJobTrue();

}
