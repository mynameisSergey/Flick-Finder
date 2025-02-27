package ru.yandex.practicum.filmorate.exсeption.validate;

public class UserLoginAlreadyExistException extends RuntimeException {
    public UserLoginAlreadyExistException(String message) {
        super(message);
    }
}