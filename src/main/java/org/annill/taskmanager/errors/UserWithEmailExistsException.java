package org.annill.taskmanager.errors;

public class UserWithEmailExistsException extends RuntimeException {
    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public UserWithEmailExistsException() {
        super("Пользователь с таким email уже существует");
    }
}
