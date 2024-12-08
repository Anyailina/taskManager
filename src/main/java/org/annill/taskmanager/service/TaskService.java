package org.annill.taskmanager.service;

import lombok.AllArgsConstructor;
import org.annill.taskmanager.Messages;
import org.annill.taskmanager.enums.Priority;
import org.annill.taskmanager.enums.Role;
import org.annill.taskmanager.enums.Status;
import org.annill.taskmanager.errors.PriorityNotFoundException;
import org.annill.taskmanager.errors.StatusNotFoundException;
import org.annill.taskmanager.errors.TaskNotFoundException;
import org.annill.taskmanager.errors.UserNotFoundException;
import org.annill.taskmanager.model.entity.Task;
import org.annill.taskmanager.model.entity.User;
import org.annill.taskmanager.repository.TaskRepository;
import org.annill.taskmanager.repository.UserRepository;
import org.annill.taskmanager.request.TaskRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final Messages messages;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public ResponseEntity<String> createTask(TaskRequest taskRequest) {
        User author = checkUser(taskRequest.getAuthorId(), Role.ROLE_ADMIN);
        User executor = checkUser(taskRequest.getExecutorId(), Role.ROLE_USER);

        Task task = new Task()
                .setTitle(taskRequest.getTitle())
                .setDescription(taskRequest.getDescription())
                .setAuthor(author)
                .setExecutor(executor)
                .setPriority(taskRequest.getPriority())
                .setStatus(taskRequest.getStatus());

        taskRepository.save(task);

        return ResponseEntity.ok(messages.getTaskWasAdded());
    }

    public ResponseEntity<Task> updateTask(Long taskId, TaskRequest updatedTask) {
        Task task = taskRepository.findById(taskId).orElseThrow(TaskNotFoundException::new);
        User author = checkUser(updatedTask.getAuthorId(), Role.ROLE_ADMIN);
        User executor = checkUser(updatedTask.getExecutorId(), Role.ROLE_USER);
        task.setTitle(updatedTask.getTitle())
                .setDescription(updatedTask.getDescription())
                .setStatus(updatedTask.getStatus())
                .setPriority(updatedTask.getPriority())
                .setExecutor(executor)
                .setAuthor(author);
        return ResponseEntity.ok(taskRepository.save(task));
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public ResponseEntity<List<Task>> getTasksByAuthor(Long authorId) {
        return ResponseEntity.ok(taskRepository.findByAuthorId(authorId));
    }

    public ResponseEntity<List<Task>> getTasksByExecutor(Long executorId) {
        return ResponseEntity.ok(taskRepository.findByExecutorId(executorId));
    }

    public ResponseEntity<String> updatePriority(Long taskId, Priority priority) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        optionalTask.map(task -> taskRepository.save(task.setPriority(priority)))
                .orElseThrow(PriorityNotFoundException::new);
        return ResponseEntity.ok(messages.getPriorityWasChanged());
    }

    public ResponseEntity<String> updateStatus(Long taskId, Status status) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        optionalTask.map(task -> taskRepository.save(task.setStatus(status)))
                .orElseThrow(StatusNotFoundException::new);
        return ResponseEntity.ok(messages.getStatusWasChanged());
    }

    public ResponseEntity<Task> getTask(Long id) {
        return ResponseEntity.ok(taskRepository.findById(id)
                .orElseThrow(TaskNotFoundException::new));
    }

    public ResponseEntity<String> updateExecutor(Long taskId, Long executorId) {
        User executor = checkUser(executorId, Role.ROLE_USER);
        taskRepository.findById(taskId)
                .map(task -> (taskRepository.save(task.setExecutor(executor))))
                .orElseThrow(UserNotFoundException::new);

        return ResponseEntity.ok(messages.getExecutorChanged());
    }

    private User checkUser(Long id, Role role) {
        Optional<User> optionalAuthor = userRepository.findById(id);
        if (optionalAuthor.isEmpty())
            throw new UserNotFoundException();

        User user = optionalAuthor.get();
        if (!user.getRole().equals(role))
            throw new UserNotFoundException();

        return user;
    }
}
