package ru.yandex.practicum.filmorate.storage.Film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component
public interface FilmStorage {

    Film showFilmById(int id);

    List<Film> showFilms();

    Film addFilm(Film film);

    Film changeFilm(Film film);

    void deleteFilmById(int id);
}
