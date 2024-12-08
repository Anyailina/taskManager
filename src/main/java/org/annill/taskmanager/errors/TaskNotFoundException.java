package org.annill.taskmanager.errors;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException() {
        super("Задача не найдена");
    }
}
