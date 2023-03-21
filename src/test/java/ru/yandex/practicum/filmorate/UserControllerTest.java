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
import ru.yandex.practicum.filmorate.controller.UserController;
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
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @Test
    public void addUsersTest() throws Exception {
        String userJson = "{\"id\":18," +
                "\"email\":\"mail@mail.ru\"," +
                "\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\"," +
                "\"birthday\":\"1946-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andExpect(content().string(userJson));
    }

    @Test
    public void showUsersTest() throws Exception {
        LocalDate dateUser = LocalDate.of(1980, 8, 12);
        User user = User.builder().name("userName").email("user@mail.ru").login("userLogin").birthday(dateUser).build();
        userController.addUser(user);

        String userJson = "[{\"id\":1,\"email\":\"user@mail.ru\"," +
                "\"login\":\"userLogin\",\"name\":\"userName\"," +
                "\"birthday\":\"1980-08-12\"}]";
        mockMvc.perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(userJson));

    }


    @Test
    public void addUsersTestFailEmail() throws Exception {
        String userJson = "{\"id\":18," +
                "\"email\":\"mailmail.ru\"," +
                "\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\"," +
                "\"birthday\":\"1946-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addUsersTestFailLogin() throws Exception {
        String userJson = "{\"id\":18," +
                "\"email\":\"mail@mail.ru\"," +
                "\"login\":\"\"," +
                "\"name\":\"Nick Name\"," +
                "\"birthday\":\"1946-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addUsersTestFailBirthday() throws Exception {
        LocalDate date = LocalDate.of(2044, 8, 12);

        String userJson = "{\"id\":18," +
                "\"email\":\"mail@mail.ru\"," +
                "\"login\":\"dolore\"," +
                "\"name\":\"Nick Name\"," +
                "\"birthday\":\"" + date + "\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addUsersTestWithEmptyName() throws Exception {
        String userJson = "{\"id\":254," +
                "\"email\":\"doloresAmbr@mail.ru\"," +
                "\"login\":\"doloresit\"," +
                "\"name\":\"\"," +
                "\"birthday\":\"1946-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON).content(userJson))
                .andExpect(status().isOk()).andExpect(content().string(containsString("\"name\":\"doloresit\"")));

    }

    @Test
    public void changeUsersTest() throws Exception {
        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(716).name("change").email("change@mail.ru").login("change").birthday(date).build();
        userController.addUser(user);

        String userJson = "{\"id\":716," +
                "\"email\":\"change@mail.ru\"," +
                "\"login\":\"changechange\"," +
                "\"name\":\"change\"," +
                "\"birthday\":\"" + date + "\"}";
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().isOk()).andExpect(content().string(userJson));


    }
    //------------------------------------------test Friends-----------------------------------------------

    @Test
    public void addFriendTest() throws Exception {
        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(1234).name("userFriend").email("userFriend@mail.ru").login("userFriend").birthday(date).build();
        userController.addUser(user);

        LocalDate date2 = LocalDate.of(1980, 8, 12);
        User friend = User.builder().id(1245).name("Friend").email("Friend@mail.ru").login("Friend").birthday(date2).build();
        userController.addUser(friend);

        mockMvc.perform(put("/users/{id}/friends/{friendId}", user.getId(), friend.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{id}/friends", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(friend.getEmail())));

        mockMvc.perform(get("/users/{id}/friends", friend.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getEmail())));

        userController.deleteUserById(user.getId());
        userController.deleteUserById(friend.getId());
    }

    @Test
    public void addFriendIncorrectIdTest() throws Exception {

        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(1234).name("userFriend").email("userFriend@mail.ru").login("userFriend").birthday(date).build();
        userController.addUser(user);

        mockMvc.perform(put("/users/{id}/friends/{friendId}", user.getId(), 111))
                .andExpect(status().is4xxClientError());

        userController.deleteUserById(user.getId());
    }

    @Test
    public void addFriendIncorrectIdOwnHimselfTest() throws Exception {

        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(1234).name("userFriend").email("userFriend@mail.ru").login("userFriend").birthday(date).build();
        userController.addUser(user);

        mockMvc.perform(put("/users/{id}/friends/{friendId}", user.getId(), user.getId()))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("The user cannot be his own friend")));

        userController.deleteUserById(user.getId());
    }

    @Test
    public void deleteFriendTest() throws Exception {
        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(1234).name("userFriend").email("userFriend@mail.ru").login("userFriend").birthday(date).build();
        userController.addUser(user);

        LocalDate date2 = LocalDate.of(1980, 8, 12);
        User friend = User.builder().id(1245).name("Friend").email("Friend@mail.ru").login("Friend").birthday(date2).build();
        userController.addUser(friend);

        mockMvc.perform(put("/users/{id}/friends/{friendId}", user.getId(), friend.getId()))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/users/{id}/friends/{friendId}", user.getId(), friend.getId()))
                .andExpect(status().isOk());

        assertEquals("Controller didn't delete friend from user", user.getFriends().size(), 0);
        assertEquals("Controller didn't delete friend from friend", friend.getFriends().size(), 0);

        userController.deleteUserById(user.getId());
        userController.deleteUserById(friend.getId());
    }

    @Test
    public void showFriendsTest() throws Exception {
        LocalDate date = LocalDate.of(1980, 8, 12);
        User user = User.builder().id(1234).name("userFriend").email("userFriend@mail.ru").login("userFriend").birthday(date).build();
        userController.addUser(user);

        LocalDate date2 = LocalDate.of(1980, 8, 12);
        User friend = User.builder().id(1245).name("Friend").email("Friend@mail.ru").login("Friend").birthday(date2).build();
        userController.addUser(friend);

        mockMvc.perform(put("/users/{id}/friends/{friendId}", user.getId(), friend.getId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/users/{id}/friends", user.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(friend.getEmail())));

        mockMvc.perform(get("/users/{id}/friends", friend.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(user.getEmail())));

        userController.deleteUserById(user.getId());
        userController.deleteUserById(friend.getId());
    }
}