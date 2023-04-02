package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilmService {

    final FilmStorage filmStorage;
    final UserStorage userStorage;

       public void addLike(int filmId, int userId) {
        Film film = filmStorage.findFilmById(filmId);
        User user = userStorage.findUserById(userId);

        film.addLike(user);
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.findFilmById(filmId);
        User user = userStorage.findUserById(userId);

        film.deleteLike(user);
    }

    public List<Film> showPopularFilms(int count) {
        return filmStorage.getFilms().stream()
                .sorted((i1, i2) -> (i2.showAmountLikes().compareTo(i1.showAmountLikes())))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film showFilmById(int id) {
        return filmStorage.findFilmById(id);
    }

    public List<Film> showFilms() {
        return filmStorage.getFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film changeFilm(Film film) {
        return filmStorage.changeFilm(film);
    }

    public void deleteFilmById(int id) {
        filmStorage.deleteFilmById(id);
    }
}
