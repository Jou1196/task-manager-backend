package com.joseph.taskmanager.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.joseph.taskmanager.dto.CreateTaskRequest;
import com.joseph.taskmanager.dto.TaskResponse;
import com.joseph.taskmanager.dto.UpdateTaskStatusRequest;
import com.joseph.taskmanager.entity.TaskPriority;
import com.joseph.taskmanager.entity.TaskStatus;
import com.joseph.taskmanager.exception.GlobalExceptionHandler;
import com.joseph.taskmanager.exception.ResourceNotFoundException;
import com.joseph.taskmanager.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(GlobalExceptionHandler.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private TaskService taskService;

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setTitle("New task");
        request.setDescription("Testing controller");
        request.setStatus(TaskStatus.PENDING);
        request.setPriority(TaskPriority.HIGH);

        TaskResponse response = TaskResponse.builder()
                .id(1L)
                .title("New task")
                .description("Testing controller")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.HIGH)
                .createdAt(LocalDateTime.now())
                .build();

        given(taskService.createTask(any(CreateTaskRequest.class))).willReturn(response);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New task"))
                .andExpect(jsonPath("$.status").value("PENDING"));
    }

    @Test
    void shouldReturnBadRequestWhenTitleIsMissing() throws Exception {
        CreateTaskRequest request = new CreateTaskRequest();
        request.setDescription("Testing validation");
        request.setStatus(TaskStatus.PENDING);
        request.setPriority(TaskPriority.HIGH);

        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Validation failed"))
                .andExpect(jsonPath("$.details.title").exists());
    }

    @Test
    void shouldGetTaskByIdSuccessfully() throws Exception {
        TaskResponse response = TaskResponse.builder()
                .id(1L)
                .title("Read task")
                .description("Testing get by id")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.MEDIUM)
                .createdAt(LocalDateTime.now())
                .build();

        given(taskService.getTaskById(1L)).willReturn(response);

        mockMvc.perform(get("/api/tasks/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Read task"));
    }

    @Test
    void shouldReturnNotFoundWhenTaskDoesNotExist() throws Exception {
        given(taskService.getTaskById(99L))
                .willThrow(new ResourceNotFoundException("Task not found with id: 99"));

        mockMvc.perform(get("/api/tasks/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Task not found with id: 99"));
    }

    @Test
    void shouldListTasksSuccessfully() throws Exception {
        TaskResponse response = TaskResponse.builder()
                .id(1L)
                .title("Task 1")
                .description("Testing list")
                .status(TaskStatus.PENDING)
                .priority(TaskPriority.LOW)
                .createdAt(LocalDateTime.now())
                .build();

        PageImpl<TaskResponse> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 10),
                1
        );

        given(taskService.getAllTasks(isNull(), eq(0), eq(10), eq("createdAt"), eq("desc")))
                .willReturn(page);

        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].title").value("Task 1"));
    }

    @Test
    void shouldUpdateTaskStatusSuccessfully() throws Exception {
        UpdateTaskStatusRequest request = new UpdateTaskStatusRequest();
        request.setStatus(TaskStatus.DONE);

        TaskResponse response = TaskResponse.builder()
                .id(1L)
                .title("Task updated")
                .description("Testing patch")
                .status(TaskStatus.DONE)
                .priority(TaskPriority.HIGH)
                .createdAt(LocalDateTime.now())
                .build();

        given(taskService.updateTaskStatus(eq(1L), eq(TaskStatus.DONE))).willReturn(response);

        mockMvc.perform(patch("/api/tasks/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("DONE"));
    }

    @Test
    void shouldDeleteTaskSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }
}
