package ru.yandex.practicum.filmorate.exceptions;

public class UserLoginAlreadyExistException extends RuntimeException {
    public UserLoginAlreadyExistException(String message) {
        super(message);
    }
}
