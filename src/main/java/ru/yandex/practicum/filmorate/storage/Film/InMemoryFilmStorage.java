package ru.yandex.practicum.filmorate.storage.Film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.ValidationException;
import java.time.LocalDate;
import java.util.*;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private final Set<String> nameFilms;

    private static int filmID = 1;
    private static final LocalDate LOCAL_DATE = LocalDate.of(1895, 12, 28);

    @Autowired
    public InMemoryFilmStorage() {
        films = new HashMap<>();
        nameFilms = new HashSet<>();
    }

    @Override
    public Film findFilmById(int id) {
        if (!(films.containsKey(id))) {
            log.warn("Не возможно найти film с id - {}.", id);
            throw new FilmException(String.format("Cannot search film by %s.", id));
        }
        return films.get(id);
    }

    @Override
    public List<Film> getFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        checkFilm(film);
        nameFilms.add(film.getName());
        films.put(film.getId(), film);
        return films.get(film.getId());
    }


    @Override
    public Film changeFilm(Film film) {
        checkFilm(film);
        films.remove(film.getId());
        films.put(film.getId(), film);
        nameFilms.add(film.getName());
        return films.get(film.getId());
    }

    @Override
    public void deleteFilmById(int id) {
        Film film = films.get(id);
        nameFilms.remove(film.getName());
        films.remove(id);
    }

    private void checkFilm(Film film) throws ValidationException { // проверка

        Film filmCheck = null;
        // для проверки меняем или создаем нового
        if (films.containsKey(film.getId())) {
            filmCheck = films.get(film.getId());
        }
        if (nameFilms.contains(film.getName())) {
            if ((filmCheck != null) & (!film.getName().equals(filmCheck.getName()))) {
                log.warn("Film with name - \"{}\" already exist", film.getName());
                throw new FilmException("Film with this name already exist. " +
                        "You cannot change Film's name.");

            } else {
                log.warn("Film with name - \"{}\" already exist", film.getName());
                throw new FilmException("Film with this name already exist. " +
                        "You cannot add Film's name.");
            }
        }

        if (film.getReleaseDate().isBefore(LOCAL_DATE)) {
            log.warn("Date is wrong {}, date must be after {}.", film.getReleaseDate(), LOCAL_DATE);
            throw new ValidationException("Date is wrong.");
        }

        //проверка id при создании
        if (filmCheck == null) {
            if (film.getId() == 0) {
                while (films.containsKey(filmID)) {
                    filmID++;
                }
                film.setId(filmID++);
            }
        }


    }

}