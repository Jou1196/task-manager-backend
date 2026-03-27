package com.joseph.taskmanager.services;


import com.joseph.taskmanager.dto.CreateTaskRequest;
import com.joseph.taskmanager.dto.TaskResponse;
import com.joseph.taskmanager.entity.Task;
import com.joseph.taskmanager.entity.TaskPriority;
import com.joseph.taskmanager.entity.TaskStatus;
import com.joseph.taskmanager.exception.ResourceNotFoundException;
import com.joseph.taskmanager.repository.TaskRepository;
import com.joseph.taskmanager.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private CreateTaskRequest createTaskRequest;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .title("Implement tests")
                .description("Create backend tests")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.HIGH)
                .createdAt(LocalDateTime.now())
                .build();

        createTaskRequest = new CreateTaskRequest();
        createTaskRequest.setTitle("Implement tests");
        createTaskRequest.setDescription("Create backend tests");
        createTaskRequest.setStatus(TaskStatus.PENDING);
        createTaskRequest.setPriority(TaskPriority.HIGH);
    }

    @Test
    void shouldCreateTaskSuccessfully() {
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponse response = taskService.createTask(createTaskRequest);

        assertNotNull(response);
        assertEquals("Implement tests", response.getTitle());
        assertEquals(TaskStatus.PENDING, response.getStatus());
        assertEquals(TaskPriority.HIGH, response.getPriority());

        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldReturnTaskByIdSuccessfully() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponse response = taskService.getTaskById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Implement tests", response.getTitle());

        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    void shouldThrowResourceNotFoundWhenTaskDoesNotExist() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.getTaskById(99L));

        verify(taskRepository, times(1)).findById(99L);
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        TaskResponse response = taskService.updateTaskStatus(1L, TaskStatus.DONE);

        assertNotNull(response);
        assertEquals(TaskStatus.DONE, response.getStatus());

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void shouldDeleteTaskSuccessfully() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        doNothing().when(taskRepository).delete(task);

        taskService.deleteTask(1L);

        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(task);
    }
}
