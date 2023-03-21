package ru.yandex.practicum.filmorate.storage.User;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
public interface UserStorage {

    List<User> showUsers();

    User showUserById(int id);

    User addUser(User user);

    User changeUser(User user);

    void deleteUserById(int id);
}