package ru.yandex.practicum.filmorate.exсeption.validate;

public class FilmIdNotNullException extends RuntimeException {
    public FilmIdNotNullException(String message) {
        super(message);
    }
}