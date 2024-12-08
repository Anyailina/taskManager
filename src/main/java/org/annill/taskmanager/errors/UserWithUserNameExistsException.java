package org.annill.taskmanager.errors;

public class UserWithUserNameExistsException extends RuntimeException {

    public UserWithUserNameExistsException() {
        super("Пользователь с таким именем уже существует");
    }
}
