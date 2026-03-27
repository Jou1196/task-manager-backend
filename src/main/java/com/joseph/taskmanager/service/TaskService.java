package com.joseph.taskmanager.service;

import com.joseph.taskmanager.dto.CreateTaskRequest;
import com.joseph.taskmanager.dto.TaskResponse;
import com.joseph.taskmanager.entity.TaskStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {

    TaskResponse createTask(CreateTaskRequest request);

    Page<TaskResponse> getAllTasks(
            TaskStatus status,
            int page,
            int size,
            String sortBy,
            String direction
    );

    TaskResponse getTaskById(Long id);

    TaskResponse updateTaskStatus(Long id, TaskStatus status);

    void deleteTask(Long id);
}
