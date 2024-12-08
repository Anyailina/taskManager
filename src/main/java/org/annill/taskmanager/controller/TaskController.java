package org.annill.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.annill.taskmanager.enums.Priority;
import org.annill.taskmanager.enums.Status;
import org.annill.taskmanager.model.entity.Task;
import org.annill.taskmanager.request.TaskRequest;
import org.annill.taskmanager.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@AllArgsConstructor
@Tag(name = "Задачи")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Создание задачи")
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> createTask(@RequestBody @Valid TaskRequest task) {
        return taskService.createTask(task);
    }

    @Operation(summary = "Изменение задачи")
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody @Valid TaskRequest task) {
        return taskService.updateTask(id, task);
    }

    @Operation(summary = "Просмотр задачи")
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Task> getTask(@PathVariable  Long id) {
        return taskService.getTask(id);
    }

    @Operation(summary = "Удаление задачи")
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTask(@PathVariable  Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получение задачи автора")
    @GetMapping("/author/{authorId}")
    public ResponseEntity<List<Task>> getTasksByAuthor(@PathVariable   Long authorId) {
        return taskService.getTasksByAuthor(authorId);
    }

    @Operation(summary = "Получение задачи исполнителя")
    @GetMapping("/executor/{executorId}")
    public ResponseEntity<List<Task>> getTasksByExecutor(@PathVariable   Long executorId) {
        return taskService.getTasksByExecutor(executorId);
    }

    @Operation(summary = "Изменение приоритета  задачи ")
    @PutMapping("/priority/{taskId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updatePriority(@PathVariable Long taskId, @RequestBody  Priority priority) {
        return taskService.updatePriority(taskId, priority);
    }

    @Operation(summary = "Получение задачи исполнителя")
    @GetMapping("/priority/{taskId}")
    public ResponseEntity<String> updateStatus(@PathVariable Long taskId, @RequestBody Status status) {
        return taskService.updateStatus(taskId, status);
    }

    @Operation(summary = "Назначить исполнителя задачи")
    @PutMapping("/{id}/executor/{executorId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<String> updateExecutor(@PathVariable Long id, @PathVariable Long executorId) {
        return taskService.updateExecutor(id, executorId);
    }
}

