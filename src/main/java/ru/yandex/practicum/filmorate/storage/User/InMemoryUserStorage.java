package ru.yandex.practicum.filmorate.storage.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.UserException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private final Set<String> logins;
    private final Set<String> emails;
    private int userId = 1;


    @Autowired
    public InMemoryUserStorage() {
        users = new HashMap<>();
        logins = new HashSet<>();
        emails = new HashSet<>();
    }

    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public User findUserById(int id) {

        if (!(users.containsKey(id))) {
            log.warn("Cannot search user by id - {}.", id);
            throw new UserException(String.format("Cannot search user by %s.", id));
        }
        return users.get(id);
    }

    @Override
    public User addUser(User user) {
        checkUser(user);
        if (!users.containsKey(user.getId())) {
            users.put(user.getId(), user);
            logins.add(user.getLogin());
            emails.add(user.getEmail());
            return user;
        } else {
            log.debug("Request form error, request must be formed PUT");
            return changeUser(user);
        }
    }

    @Override
    public User changeUser(User user) {
        if (!users.containsKey(user.getId())) {
            log.debug("Request form error, request must have format POST");
            return addUser(user);
        } else {
            checkUser(user);
            User checkUser = users.get(user.getId());
            deleteUserById(checkUser.getId());

            logins.add(user.getLogin());
            emails.add(user.getEmail());
            users.put(user.getId(), user);
            return user;
        }
    }

    @Override
    public void deleteUserById(int id) {
        User user = users.get(id);
        users.remove(user.getId());
        logins.remove(user.getLogin());
        emails.remove(user.getEmail());
    }

    private void checkUser(User user) {
        User userCheck = null;
        // для проверки меняем или создаем нового
        if (users.containsKey(user.getId())) {
            userCheck = users.get(user.getId());
        }

        // проверка логина при создании и обновлении
        if (logins.contains(user.getLogin())) {
            if ((userCheck != null) & (!(user.getLogin().equals(userCheck.getLogin())))) {

                log.warn("User with login - \"{}\" already exist", user.getLogin());
                throw new UserException("User with this login already exist. " +
                        "You cannot change User's login.");
            } else {
                log.warn("User with login - \"{}\" already exist", user.getLogin());
                throw new UserException("User with this login already exist. " +
                        "You cannot add User.");
            }
        }

        // проверка эмейла при создании и обновлении
        if (emails.contains(user.getEmail()))  {
            if (userCheck != null) {
                if (!(user.getEmail().equals(userCheck.getEmail()))) {
                    log.warn("User with login - \"{}\" already exist", user.getEmail());
                    throw new UserException("User with this Email already exist. " +
                            "You cannot change User's login.");
                }
            } else {
                log.warn("User with Email - \"{}\" already exist", user.getEmail());
                throw new UserException("User with this Email already exist. " +
                        "You cannot add User.");
            }
        }

        //проверка id при создании
        if (userCheck == null) {
            if (user.getId() == 0) {
                while (users.containsKey(userId)) {
                    userId++;
                }
                user.setId(userId++);
            }
        }

        if (user.getName().isBlank()) {
            log.debug("User don't have name");
            user.setName(user.getLogin());
        }
    }
}