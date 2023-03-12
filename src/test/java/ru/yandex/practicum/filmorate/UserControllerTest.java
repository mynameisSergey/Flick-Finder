package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashSet;


import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest

public class UserControllerTest {

    UserController userController = new UserController();
    User user;
    User user1;
    User user2;
    User user3;
    User user4;
    User user5;
    User user6;
    User user7;
    User user8;


    @BeforeEach
    public void beforeEach() {

        user = new User("serg@mail.ru", "serg", LocalDate.of(1990, 11, 22));
        user1 = new User("serg.2022 mail.ru", "S erg", LocalDate.of(1990, 2, 22));
        user2 = new User("serg@mail.ru", "serg", LocalDate.of(1990, 11, 22));
        user3 = new User(null, "Serg", LocalDate.of(1990, 11, 22));
        user4 = new User("serg@mail.ru", "serg", LocalDate.of(1996, 11, 22));
        user5 = new User("serg@mail.ru", null, LocalDate.of(1996, 11, 22));
        user6 = new User("serg@mail.ru", "serg", LocalDate.of(1996, 11, 22));
        user7 = new User("serg@mail.ru", "serg", LocalDate.of(2025, 5, 22));
        user8 = new User("serg@mail.ru", "S erg", LocalDate.of(1990, 11, 22));

    }

    @Test
    void validationUser() throws ValidationException {
        User newUser = userController.create(user);
        HashSet<User> users = userController.findAllUsers();

        assertNotNull(users, "Список пользователей пустой.");
        assertEquals(1, users.size(), "Количество пользователей не соответствует");
    }


    @Test
    void validationExceptionEmailUser() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user3)
        );
        assertEquals("Адрес электронной почты не может быть пустым.", e.getMessage());
    }

    @Test
    void validationExceptionEmailContainsUser() throws ValidationException {
        userController.create(user);
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user4)
        );
        assertEquals("Пользователь с электронной почтой serg@mail.ru уже зарегистрирован.", e.getMessage());
    }

    @Test
    void validationExceptionEmail() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user1)
        );
        assertEquals("Адрес электронной почты не содержит @.", e.getMessage());

    }

    @Test
    void validationExceptionNullLoginUser() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user5)
        );
        assertEquals("Логин не может быть пустым.", e.getMessage());
    }

    @Test
    void validationExceptionTrimLoginUser() {
        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user8)
        );
        assertEquals("Логин не может содержать пробелы.", e.getMessage());
    }

    @Test
    void validationExceptionNameUser() throws ValidationException {
        User newUser = userController.create(user6);
        assertEquals("serg", user6.getName());
    }

    @Test
    void validationExceptionBirthdayUser() {

        ValidationException e = assertThrows(

                ValidationException.class,
                () -> userController.create(user7)
        );
        assertEquals("Дата рождения не может быть в будущем", e.getMessage());

    }
}