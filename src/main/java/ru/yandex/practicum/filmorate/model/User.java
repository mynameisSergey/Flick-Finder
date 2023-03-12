package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private int id;
    private String email;
    private String login;
    private String name = null;
    private LocalDate birthday;

    public User(String email, String login, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.birthday = birthday;
    }
}