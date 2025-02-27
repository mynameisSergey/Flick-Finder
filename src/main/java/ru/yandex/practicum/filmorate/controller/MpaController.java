package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exсeption.validate.IncorrectParamException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
@RequiredArgsConstructor
public class MpaController {
    private final MpaService mpaService;

    @GetMapping
    public List<Mpa> getMpaRatings() {
        log.info("Запрос на получение всех рейтингов MPA.");
        return mpaService.getAll();
    }

    @GetMapping("/{id}")
    public Mpa getMpa(@PathVariable int id) {
        log.info("Запрос на получение рейтинга MPA с id = {}.", id);
        Mpa mpa = mpaService.showById(id);

        if (mpa == null) {
            throw new IncorrectParamException("Рейтинг MPA с id " + id + " не найден.");
        }

        return mpa;
    }
}
