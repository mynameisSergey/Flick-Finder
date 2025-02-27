package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Like {

    private Integer filmID;
    private Integer userID;

    public Like(Integer filmID, Integer userID) {
        if (filmID == null) {
            throw new IllegalArgumentException("Film ID cannot be null");
        }
        if (userID == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        this.filmID = filmID;
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return Objects.equals(filmID, like.filmID) &&
                Objects.equals(userID, like.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(filmID, userID);
    }
}
