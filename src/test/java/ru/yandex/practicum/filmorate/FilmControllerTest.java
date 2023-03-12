package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.filmorate.model.Film;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FilmControllerTest {
    FilmController filmController = new FilmController();
    Film film;
    Film film1;
    Film film2;
    Film film3;
    Film film4;

    @BeforeEach
    public void beforeEach() {

        film = new Film("Терминатор", "Про роботов", LocalDate.of(1985, 01, 01), 145);
        film1 = new Film("", "Про роботов", LocalDate.of(1985, 01, 01), 145);
        film2 = new Film("Терминатор", "американский научно-фантастический фильм......................................................................................................................................................................................................................................................................................................................................................", LocalDate.of(1985, 01, 01), 145);
        film3 = new Film("Терминатор2", "американский эпический научно-фантастический фильм", LocalDate.of(1477, 07, 07), 242);
        film4 = new Film("Терминатор3", "Продолжение 2 части", LocalDate.of(2022, 12, 10), -11);
    }

    @Test
    void validationFilm() throws ValidationException {
        Film newFilm = filmController.create(film);
        HashSet<Film> films = filmController.findAllFilms();

        assertNotNull(films, "Список фильмов пустой.");
        assertEquals(1, films.size(), "Количество фильмов не соответствует");
    }


    @Test
    void validationExceptionNullNameFilm() throws ValidationException {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> filmController.create(film1)
        );
        assertEquals("Название фильма не может быть пустым.", e.getMessage());
    }

    @Test
    void validationExceptionDescriptionFilm() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> filmController.create(film2)
        );
        assertEquals("В фильме " +
                film.getName() + " описание превышает или равно 200 символам.", e.getMessage());
    }


    @Test
    void validationExceptionReleaseDateFilm() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> filmController.create(film3)
        );
        assertEquals("У фильма " +
                film3.getName() + " дата релиза — раньше 28 декабря 1895 года", e.getMessage());
    }

    @Test
    void validationExceptionDurationFilm() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> filmController.create(film4)
        );
        assertEquals("В фильме " +
                film4.getName() + " продолжительность должна быть положительной", e.getMessage());
    }
}
