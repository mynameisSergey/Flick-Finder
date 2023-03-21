package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectParamException extends RuntimeException{
    public IncorrectParamException(String message) {
        super(message);
    }
}
