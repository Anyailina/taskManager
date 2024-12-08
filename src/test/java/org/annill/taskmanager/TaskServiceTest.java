package org.annill.taskmanager;

import org.annill.taskmanager.enums.Priority;
import org.annill.taskmanager.enums.Role;
import org.annill.taskmanager.enums.Status;
import org.annill.taskmanager.errors.TaskNotFoundException;
import org.annill.taskmanager.model.entity.Task;
import org.annill.taskmanager.model.entity.User;
import org.annill.taskmanager.repository.TaskRepository;
import org.annill.taskmanager.repository.UserRepository;
import org.annill.taskmanager.request.TaskRequest;
import org.annill.taskmanager.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Messages messages;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTask_ShouldSaveTaskAndReturnSuccessMessage() {
        TaskRequest taskRequest = new TaskRequest();
        taskRequest.setAuthorId(1L);
        taskRequest.setExecutorId(2L);
        taskRequest.setTitle("Test Task");
        taskRequest.setDescription("Test Description");
        taskRequest.setPriority(Priority.HIGH);
        taskRequest.setStatus(Status.IN_PROGRESS);

        User author = new User();
        author.setId(1L);
        author.setRole(Role.ROLE_ADMIN);

        User executor = new User();
        executor.setId(2L);
        executor.setRole(Role.ROLE_USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(author));
        when(userRepository.findById(2L)).thenReturn(Optional.of(executor));
        when(messages.getTaskWasAdded()).thenReturn("Задача была добавлена");

        ResponseEntity<String> response = taskService.createTask(taskRequest);

        assertEquals("Задача была добавлена", response.getBody());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void getTask_ShouldReturnTaskWhenExists() {
        Long taskId = 1L;
        Task task = new Task();
        task.setId(taskId);

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        ResponseEntity<Task> response = taskService.getTask(taskId);

        assertNotNull(response.getBody());
        assertEquals(taskId, response.getBody().getId());
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void getTask_ShouldThrowTaskNotFoundExceptionWhenTaskDoesNotExist() {
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.getTask(taskId));
        verify(taskRepository, times(1)).findById(taskId);
    }

    @Test
    void deleteTask_ShouldCallRepositoryDeleteById() {
        Long taskId = 1L;

        doNothing().when(taskRepository).deleteById(taskId);

        taskService.deleteTask(taskId);

        verify(taskRepository, times(1)).deleteById(taskId);
    }

}
