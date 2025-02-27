package ru.yandex.practicum.filmorate.exсeption.validate;

public class IncorrectParamException extends RuntimeException {
    public IncorrectParamException(String message) {
        super(message);
    }
}