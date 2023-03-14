package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.*;
import ru.yandex.practicum.filmorate.model.Film;
import org.springframework.util.StringUtils;


import java.time.LocalDate;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/films")
public class FilmController {

    private final Set<Film> films = new HashSet<>();
    private int idGenerator = 1;

    @GetMapping()
    public Set<Film> findAllFilms() {
        return films;
    }

    @PostMapping
    public Film create(@RequestBody Film film) throws ValidationException {
        if (validation(film)) {
            film.setId(idGenerator++);
            films.add(film);
            log.info("Добавили фильм {}", film.getName());
            return film;
        }
        return film;
    }

    @PutMapping
    public Film update(@RequestBody Film newFilm) throws ValidationException {
        if (validation(newFilm)) {
            for (Film film : films) {
                if (film.getId() == newFilm.getId()) {
                    films.remove(film);
                    films.add(newFilm);
                    log.info("Обновлен фильм {}", newFilm.getName());
                    return newFilm;
                } else {
                    log.warn("Обновление не произошло");
                    throw new ValidationException("Фильма :" + newFilm.getName() + "не существует");
                }
            }
        }
        return newFilm;
    }

    private boolean validation(Film film) throws ValidationException {
        if (!StringUtils.hasText(film.getName())) {
            log.warn("Название фильма id: {} отсутствует ", film.getId());
            throw new ValidationException("Название фильма не может быть пустым.");
        }
        if (film.getDescription().length() >= 200) {
            log.warn("Описание фильма {}: неподходящая длинна описания фильма {} ", film.getName(), film.getDescription().length());
            throw new ValidationException("В фильме " +
                    film.getName() + " описание превышает или равно 200 символам.");
        }
        if (film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.warn("У фильма {}: неподходящая дата релиза {} ", film.getName(), film.getReleaseDate());
            throw new ValidationException("У фильма " +
                    film.getName() + " дата релиза — раньше 28 декабря 1895 года");
        }
        if (film.getDuration() < 0) {
            log.warn("Фильм {} длительность отрицательная {} ", film.getName(), film.getDuration());
            throw new ValidationException("В фильме " +
                    film.getName() + " продолжительность должна быть положительной");
        }
        return true;
    }
}