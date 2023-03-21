package ru.yandex.practicum.filmorate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"ru.yandex.practicum.filmorate"})
public class FilmControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FilmController filmController;
    @Autowired
    private UserController userController;


    @Test
    public void showFilmsTest() throws Exception {
        Film film = addFilm();

        String filmJson = "\"name\":\"filmName\",\"description\":\"filmDescription\"," +
                "\"releaseDate\":\"2022-12-13\"";
        mockMvc.perform(get("/films"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(filmJson)));

        filmController.deleteFilmById(film.getId());
    }

    @Test
    public void showFilmByIdTest() throws Exception {
        Film film = addFilm();

        String filmJson = "\"name\":\"filmName\"," +
                "\"description\":\"filmDescription\",\"releaseDate\":\"2022-12-13\"";
        mockMvc.perform(get("/films/" + film.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(filmJson)));


        filmController.deleteFilmById(film.getId());
    }

    @Test
    public void showFilmByIdWrongTest() throws Exception {
        mockMvc.perform(get("/films/-1"))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Cannot search film by -1")));

    }

    @Test
    public void addFilmTest() throws Exception {
        String filmJson = "{\"id\":155,\"name\":\"addfilm\"," +
                "\"description\":\"addfilm\",\"releaseDate\":\"2022-12-13\",\"duration\":12}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isOk()).andExpect(content().string(filmJson));

        filmController.deleteFilmById(155);
    }

    @Test
    public void addFilmFailNameTest() throws Exception {
        String filmJson = "{\"name\":\"\",\"description\":\"asd\",\"releaseDate\":\"2022-12-13\",\"duration\":12}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addFilmFailLDescriptionMin1Test() throws Exception {
        String filmJson = "{\"id\":3," +
                "\"name\":\"asd\"," +
                "\"\":\"asd\"," +
                "\"releaseDate\":\"2022-12-13\"," +
                "\"duration\":12}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addFilmFailLDescriptionMax200Test() throws Exception {

        String description = "a".repeat(201);

        String filmJson = "{\"id\":3," +
                "\"name\":\"asd\"," +
                "\"" + description + "\":\"asd\"," +
                "\"releaseDate\":\"2022-12-13\"," +
                "\"duration\":12}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addFilmFailReleaseDateTest() throws Exception {
        String filmJson = "{\"id\":3,\"name\":\"asdtr\"," +
                "\"description\":\"asd\",\"releaseDate\":\"1888-12-13\",\"duration\":12}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addFilmFailDurationTest() throws Exception {
        String filmJson = "{\"id\":3,\"name\":\"asd\"," +
                "\"description\":\"asd\",\"releaseDate\":\"2022-12-13\",\"duration\":-1}";

        mockMvc.perform(post("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void putFilmTest() throws Exception {
        Film film = addFilm();

        String filmJson = "{\"id\":1,\"name\":\"filmNamePUT\"," +
                "\"description\":\"filmDescription\",\"releaseDate\":\"2022-12-13\",\"duration\":12}";

        mockMvc.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(filmJson))
                .andExpect(status().isOk())
                .andExpect(content().string(filmJson));

        filmController.deleteFilmById(1);
        filmController.deleteFilmById(film.getId());
    }


    //______________________________________________Test like______________________________________

    @Test
    public void addLikeTest() throws Exception {
        Film film = addFilm();
        User user = addUser();

        mockMvc.perform(put("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        assertEquals("Like don't add in film", (Integer) 1, film.showAmountLikes());

        filmController.deleteFilmById(film.getId());
        userController.deleteUserById(user.getId());
    }

    @Test
    public void deleteLikeTest() throws Exception {
        Film film = addFilm();
        User user = addUser();

        mockMvc.perform(put("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        assertEquals("Like don't add in film", (Integer) 0, film.showAmountLikes());

        filmController.deleteFilmById(film.getId());
        userController.deleteUserById(user.getId());
    }

    @Test
    public void showPopularFilmsDefaultTest() throws Exception {
        Film film = addFilm();
        User user = addUser();

        String popular = "\"name\":\"filmName\",\"description\":\"filmDescription\"," +
                "\"releaseDate\":\"2022-12-13\"";
        mockMvc.perform(put("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/films/popular"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(popular)));


        filmController.deleteFilmById(film.getId());
        userController.deleteUserById(user.getId());
    }

    @Test
    public void showPopular5FilmsTest() throws Exception {
        Film film = addFilm();
        User user = addUser();

        String popular = "\"name\":\"filmName\",\"description\":\"filmDescription\"," +
                "\"releaseDate\":\"2022-12-13\"";
        mockMvc.perform(put("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/films/popular?count={count}", 5))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(popular)));


        filmController.deleteFilmById(film.getId());
        userController.deleteUserById(user.getId());
    }

    @Test
    public void showPopularFilmsIncorrectCountTest() throws Exception {
        Film film = addFilm();
        User user = addUser();

        mockMvc.perform(put("/films/{id}/like/{userId}", film.getId(), user.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(get("/films/popular?count={count}", -1))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("incorrect")));


        filmController.deleteFilmById(film.getId());
        userController.deleteUserById(user.getId());
    }

    private Film addFilm() {
        LocalDate date = LocalDate.of(2022, 12, 13);
        Film film = Film.builder().name("filmName").description("filmDescription").releaseDate(date).duration(12).build();
        filmController.addFilm(film);
        return film;
    }

    private User addUser() {
        LocalDate dateUser = LocalDate.of(1980, 8, 12);
        User user = User.builder().name("userName").email("user@mail.ru").login("userLogin").birthday(dateUser).build();
        userController.addUser(user);
        return user;
    }

}
