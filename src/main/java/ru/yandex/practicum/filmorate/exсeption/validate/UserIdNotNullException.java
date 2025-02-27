package ru.yandex.practicum.filmorate.exсeption.validate;

public class UserIdNotNullException extends RuntimeException {
    public UserIdNotNullException(String message) {
        super(message);
    }
}