package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = {"ru.yandex.practicum.filmorate"})
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @Test
    public void addUsersTestFailEmail() throws Exception {
        String userJson = "{\"login\": \"dolore\"," +
                " \"name\": \"Nick Name\"," +
                " \"email\": \"mailmailru\"," +
                "  \"birthday\": \"1946-08-20\"}";

        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addUsersTestFailLogin() throws Exception {
        String userJson = "{\"login\": \"\"," +
                " \"name\": \"Nick Name\"," +
                " \"email\": \"mail@mail.ru\"," +
                "  \"birthday\": \"1946-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addUsersTestFailBirthday() throws Exception {
        String userJson = "{\"login\": \"dolore\"," +
                " \"name\": \"Nick Name\"," +
                " \"email\": \"mail@mail.ru\"," +
                "  \"birthday\": \"2046-08-20\"}";
        mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(userJson))
                .andExpect(status().is4xxClientError());

    }


    @Test
    public void addFriendIncorrectIdTest() throws Exception {


        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 111))
                .andExpect(status().is4xxClientError());

    }

    @Test
    public void addFriendIncorrectIdOwnHimselfTest() throws Exception {


        mockMvc.perform(put("/users/{id}/friends/{friendId}", 1, 1))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("The user cannot be his own friend")));

    }

}