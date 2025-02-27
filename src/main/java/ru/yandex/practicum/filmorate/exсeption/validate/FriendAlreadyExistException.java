package ru.yandex.practicum.filmorate.exсeption.validate;

public class FriendAlreadyExistException extends RuntimeException {
    public FriendAlreadyExistException(String message) {
        super(message);
    }
}