package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Film {
    private int id;

    @NotNull(message = "Film name cannot be null")
    @NotEmpty(message = "Film name cannot be empty")
    @NotBlank(message = "Film name cannot be blank")
    private String name;

    @NotEmpty(message = "Film description cannot be empty")
    @Size(max = 200, message = "Film description must be less than 200 characters.")
    private String description;

    @PastOrPresent(message = "Release date must be in the past or present")
    private LocalDate releaseDate;

    @Min(value = 1L, message = "Film duration must be greater than 0")
    private long duration;

    @NotNull(message = "Film needs to be rated")
    private Mpa mpa;

    private Set<Genre> genres = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return id == film.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
