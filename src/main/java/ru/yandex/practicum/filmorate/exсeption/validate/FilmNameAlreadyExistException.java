package ru.yandex.practicum.filmorate.exсeption.validate;

public class FilmNameAlreadyExistException extends RuntimeException {
    public FilmNameAlreadyExistException(String message) {
        super(message);
    }
}