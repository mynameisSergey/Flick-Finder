package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.dao.genre.GenreDao;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreDao genreDao;

    public Genre get(int id) {
        log.info("Fetching genre with id: {}", id);
        return Optional.ofNullable(genreDao.showGenreById(id))
                .orElseThrow(() -> new RuntimeException("Genre not found with id: " + id));
    }

    public List<Genre> getAll() {
        log.info("Fetching all genres");
        return genreDao.showGenres();
    }
}