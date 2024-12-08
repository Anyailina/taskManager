package org.annill.taskmanager;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class Messages {
    private final String taskWasAdded = "Задача была добавлена";
    private final String priorityWasChanged = "Приоритет задачи был изменен";
    private final String statusWasChanged = "Статус задачи был изменен";
    private final String executorChanged = "Исполнитель задачи был изменен";
    private final String commentWasAdded = "Комментарий был добавлен";
}
