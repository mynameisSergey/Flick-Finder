package ru.yandex.practicum.filmorate.exceptions;


public class FilmNameAlreadyExistException extends RuntimeException{
    public FilmNameAlreadyExistException(String message) {
        super(message);
    }
}