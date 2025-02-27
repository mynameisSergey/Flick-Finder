package ru.yandex.practicum.filmorate.exсeption.notfound;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
