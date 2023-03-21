package ru.yandex.practicum.filmorate.exceptions;

public class UserEmailAlreadyExistException extends RuntimeException {
    public UserEmailAlreadyExistException(String message) {
        super(message);
    }
}