package org.annill.taskmanager.errors;

public class PriorityNotFoundException extends RuntimeException {
    public PriorityNotFoundException() {
        super("Приоритет не найден");
    }
}
