package org.annill.taskmanager.service;

import lombok.AllArgsConstructor;
import org.annill.taskmanager.Messages;
import org.annill.taskmanager.errors.TaskNotFoundException;
import org.annill.taskmanager.model.entity.Comment;
import org.annill.taskmanager.model.entity.Task;
import org.annill.taskmanager.repository.CommentRepository;
import org.annill.taskmanager.repository.TaskRepository;
import org.annill.taskmanager.request.CommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final Messages messages;

    public ResponseEntity<String> create(CommentRequest commentRequest) {
        Optional<Task> optionalTask = taskRepository.findById(commentRequest.getTaskId());

        if (optionalTask.isEmpty())
            throw new TaskNotFoundException();
        Comment comment = new Comment()
                .setContent(commentRequest.getContent())
                .setTask(optionalTask.get());

        commentRepository.save(comment);
        return ResponseEntity.ok(messages.getCommentWasAdded());
    }
}
