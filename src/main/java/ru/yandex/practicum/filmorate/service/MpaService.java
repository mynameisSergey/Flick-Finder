package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.dao.mpa.MpaDao;

import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {
    private final MpaDao mpaDao;

    public Mpa showById(int id) {
        log.info("Fetching MPA with id: {}", id);
        return Optional.ofNullable(mpaDao.showById(id))
                .orElseThrow(() -> new RuntimeException("MPA not found with id: " + id));
    }

    public List<Mpa> getAll() {
        log.info("Fetching all MPA ratings");
        return mpaDao.getAll();
    }
}