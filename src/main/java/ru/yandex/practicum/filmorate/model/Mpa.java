package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@NoArgsConstructor
public class Mpa {

    private Integer id;
    private String name;

    public Mpa(Integer id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null");
        }
        this.id = id;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mpa mpa = (Mpa) o;
        return Objects.equals(id, mpa.id) &&
                Objects.equals(name, mpa.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
