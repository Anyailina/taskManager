package org.annill.taskmanager.errors;


public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException() {
        super("Статус не найден");
    }
}
