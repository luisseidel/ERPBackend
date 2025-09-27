package com.seidelsoft.ERPBackend.taskManager.service;

import com.seidelsoft.ERPBackend.system.service.BaseService;
import com.seidelsoft.ERPBackend.taskManager.model.TaskType;
import com.seidelsoft.ERPBackend.taskManager.repository.TaskTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class TaskTypeService extends BaseService<TaskType, TaskTypeRepository> {
}
