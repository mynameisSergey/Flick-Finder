package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Film {
    private int id;

    @NotEmpty(message = "Film name cannot be not empty")
    private String name;

    @NotEmpty
    @Size(max = 200, message = "Film description must be less than 200 characters.")
    private String description;

    private LocalDate releaseDate;

    @Min(value = 0L, message = "Film duration must be more that 0")
    private long duration;
    private Genre genre;
    private Rate rate;
    @JsonIgnore
    private final Map<User, Integer> likes = new HashMap<>();

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Film film = (Film) o;

        return id == film.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    public void addLike(User user) { // добавить лайк
        likes.put(user, 0);
    }

    public void deleteLike(User user) { // удалить лайк
        likes.remove(user);
    }

    public Integer showAmountLikes() { // показать количество лайков
        return getLikes().size();
    }

    public enum Genre {ACTION, COMEDY, DRAMA, FANTASY, HORROR, ROMANCE, THRILLER, WESTERN}
    public enum Rate {G, PG, PG_13, R, NC_17}
}