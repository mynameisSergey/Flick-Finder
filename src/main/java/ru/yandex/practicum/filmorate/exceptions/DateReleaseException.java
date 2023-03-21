package ru.yandex.practicum.filmorate.exceptions;

public class DateReleaseException extends RuntimeException {
    public DateReleaseException(String message) {
        super(message);
    }
}