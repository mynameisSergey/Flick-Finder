package ru.yandex.practicum.filmorate.exсeption.notfound;

public class GenreNotFoundException extends NotFoundException {
    public GenreNotFoundException(String message) {
        super(message);
    }
}