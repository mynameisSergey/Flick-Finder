package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setEmail(rs.getString("email"));
        user.setLogin(rs.getString("login"));

        String name = rs.getString("name");
        if (name != null)
            user.setName(name);
        else
            user.setName(""); // Или любое другое значение по умолчанию


        // Проверка на null для даты рождения
        java.sql.Date birthday = rs.getDate("birthday");
        if (birthday != null)
            user.setBirthday(birthday.toLocalDate());
        else
            user.setBirthday(null); // Или любое другое значение по умолчанию

        return user;
    }
}