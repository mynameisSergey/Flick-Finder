package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriend(int userID, int friendID) {
        User user = getUserById(userID);
        User friend = getUserById(friendID);

        user.addFriend(friend);
        friend.addFriend(user);
    }

    public void deleteFriend(int userID, int friendID) {
        User user = getUserById(userID);
        User friend = getUserById(friendID);

        user.removeFriend(friend);
        friend.removeFriend(user);
    }

    public Set<User> showCommonFriends(int userID, int otherUserID) {
        User user = getUserById(userID);
        User otherUser = getUserById(otherUserID);

        Set<User> users = new HashSet<>(user.getFriends());
        users.retainAll(otherUser.getFriends());
        return users;
    }

    public Set<User> showFriends(int userID) {
        User user = getUserById(userID);

        return user.getFriends();
    }

    public List<User> showUsers() {
        return userStorage.getUsers();
    }

    public User showUserById(int id) {
        return userStorage.findUserById(id);
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User changeUser(User user) {
        return userStorage.changeUser(user);
    }

    public void deleteUserById(int id) {
        userStorage.deleteUserById(id);
    }

    public User getUserById(int id) {
        return userStorage.findUserById(id);
    }
}
