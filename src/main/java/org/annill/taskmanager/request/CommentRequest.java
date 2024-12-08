package org.annill.taskmanager.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Запрос на добавление комментария")
public class CommentRequest {

    @Schema(description = "Содержимое комментария", example = "Задача выполнена")
    @NotBlank(message = "Содержимое комментария не может быть пустым")
    private String content;

    @Schema(description = "Идентификатор задачи", example = "42")
    @NotBlank(message = "Содержимое id задачи не может быть пустым")
    private Long taskId;
}
