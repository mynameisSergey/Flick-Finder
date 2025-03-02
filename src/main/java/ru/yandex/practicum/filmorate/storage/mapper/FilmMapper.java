package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;


public class FilmMapper implements RowMapper<Film> {
    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Mpa mpa = new Mpa();
        mpa.setId(rs.getInt("mpa_rating_id"));
        mpa.setName(rs.getString("mpa_name"));

        Film film = new Film();
        film.setId(rs.getInt("film_id"));
        film.setName(rs.getString("name"));
        film.setDescription(rs.getString("description"));

        if (rs.getDate("release_date") != null) {
            film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        } else {
            film.setReleaseDate(null); // Или установите значение по умолчанию
        }

        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration_in_minutes"));
        film.setMpa(mpa);
        return film;
    }

}