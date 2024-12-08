package org.annill.taskmanager.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.annill.taskmanager.request.CommentRequest;
import org.annill.taskmanager.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
@AllArgsConstructor
@Tag(name = "Комментарии")
public class CommentController {
    private final CommentService commentService;

    @Operation(summary = "Создание комментария")
    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest) {
        return commentService.create(commentRequest);
    }
}
