package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.Film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.User.UserStorage;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    final FilmStorage filmStorage;
    final UserStorage userStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage, UserStorage userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void addLike(int filmId, int userId) {
        Film film = filmStorage.showFilmById(filmId);
        User user = userStorage.showUserById(userId);

        film.addLike(user);
    }

    public void deleteLike(int filmId, int userId) {
        Film film = filmStorage.showFilmById(filmId);
        User user = userStorage.showUserById(userId);

        film.deleteLike(user);
    }

    public List<Film> showPopularFilms(int count) {
        return filmStorage.showFilms().stream()
                .sorted((i1, i2) -> (i2.showAmountLikes().compareTo(i1.showAmountLikes())))
                .limit(count)
                .collect(Collectors.toList());
    }

    public Film showFilmById(int id) {
        return filmStorage.showFilmById(id);
    }

    public List<Film> showFilms() {
        return filmStorage.showFilms();
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
