package org.annill.taskmanager.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.annill.taskmanager.enums.Priority;
import org.annill.taskmanager.enums.Status;

@Data
@Schema(description = "Запрос на создание задачи")
public class TaskRequest {

    @Schema(description = "Заголовок задачи", example = "Создать документацию")
    @NotBlank(message = "Заголовок не может быть пустым")
    private String title;

    @Schema(description = "Описание задачи", example = "Необходимо создать документацию для нового модуля")
    @NotBlank(message = "Описание не может быть пустым")
    private String description;

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    @NotBlank(message = "Статус не может быть пустым")
    private Status status;

    @Schema(description = "Приоритет задачи", example = "HIGH")
    @NotBlank(message = "Приоритет не может быть пустым")
    private Priority priority;

    @Schema(description = "Идентификатор автора задачи", example = "1")
    @NotBlank(message = "Id автора не может быть пустым")
    private Long authorId;

    @Schema(description = "Идентификатор исполнителя задачи", example = "2")
    @NotBlank(message = "Id исполнителя не может быть пустым")
    private Long executorId;
}