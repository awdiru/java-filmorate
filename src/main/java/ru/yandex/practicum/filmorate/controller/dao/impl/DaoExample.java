package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class DaoExample {
    static Film makeFilm(ResultSet resultSet, JdbcTemplate jdbcTemplate) throws SQLException {
        Integer filmId = resultSet.getInt("film_id");
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        Integer duration = resultSet.getInt("duration");
        Integer ratingId = resultSet.getInt("rating_id");

        String sqlLikes = "SELECT user_id FROM likes WHERE film_id = ?";
        Set<Integer> likes = new HashSet<>(jdbcTemplate.query(sqlLikes, (rs, rowNum) -> rs.getInt("user_id"), filmId));

        return new Film(filmId, name, description, releaseDate, duration, ratingId, likes);
    }

    static User makeUser(ResultSet resultSet, JdbcTemplate jdbcTemplate) throws SQLException {
        Integer id = resultSet.getInt("user_id");
        String email = resultSet.getString("email");
        String login = resultSet.getString("login");
        String name = resultSet.getString("name");
        LocalDate birthday = resultSet.getDate("birthday").toLocalDate();

        String sql = "SELECT friend_id FROM friends WHERE user_id = ?";
        Set<Integer> friends = new HashSet<>(jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("friend_id"), id));

        return new User(id, email, login, name, birthday, friends);
    }
}
