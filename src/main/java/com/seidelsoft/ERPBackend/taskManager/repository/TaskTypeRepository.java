package com.seidelsoft.ERPBackend.taskManager.repository;

import com.seidelsoft.ERPBackend.taskManager.model.TaskType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskTypeRepository extends JpaRepository<TaskType, Long> {
}
