package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friend {

    private Integer userId;
    private Integer friendId;

    private boolean status; // Примитивный тип для статуса

    public Friend(Integer userId, Integer friendId) {
        if (userId == null || friendId == null) {
            throw new IllegalArgumentException("User ID and Friend ID cannot be null");
        }
        this.userId = userId;
        this.friendId = friendId;
        this.status = false; // Установите значение по умолчанию для статуса
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Friend friend = (Friend) o;
        return Objects.equals(userId, friend.userId) &&
                Objects.equals(friendId, friend.friendId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, friendId);
    }
}
