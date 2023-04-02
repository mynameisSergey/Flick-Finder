package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class User {

    private int id;
    @Email(message = "It's not email.")
    private String email;
    @NotBlank(message = "User login cannot be empty and contains spaces.")
    private String login;
    private String name;
    @Past(message = "date of birth cannot be in the future.")
    private LocalDate birthday;
    @JsonIgnore
    private final Set<User> friends = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addFriend(User user) { // добавить в друзья
        friends.add(user);
    }

    public void removeFriend(User user) { // удалить из друзей
        friends.remove(user);
    }
}