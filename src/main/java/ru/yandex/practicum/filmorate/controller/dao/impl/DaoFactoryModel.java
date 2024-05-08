package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class DaoFactoryModel {
    static Film makeFilm(ResultSet resultSet, JdbcTemplate jdbcTemplate) throws SQLException {
        Integer filmId = resultSet.getInt("film_id");
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        Integer duration = resultSet.getInt("duration");
        Integer ratingId = resultSet.getInt("rating_id");

        String sqlRating = "SELECT * FROM ratings WHERE rating_id = ?";
        Rating rating = jdbcTemplate.queryForObject(sqlRating, (rs, rowNum) -> makeRating(rs), ratingId);

        String sqlLikes = "SELECT user_id FROM likes WHERE film_id = ?";
        List<Integer> likes = jdbcTemplate.query(sqlLikes, (rs, rowNum) -> rs.getInt("user_id"), filmId);

        String sqlGenres = "SELECT * FROM genres WHERE genre_id IN " +
                "(SELECT genre_id FROM film_genre WHERE film_id = ? ORDER BY genre_id)";
        List<Genre> genres = jdbcTemplate.query(sqlGenres, (rs, rowNum) -> makeGenre(rs), filmId);

        String sqlDirectors = "SELECT * FROM directors WHERE director_id IN " +
                "(SELECT director_id FROM film_director WHERE film_id = ? ORDER BY director_id)";
        List<Director> directors = jdbcTemplate.query(sqlDirectors, (rs, rowNum) -> makeDirector(rs), filmId);

        return new Film(filmId, name, description, releaseDate, duration, rating, likes, genres, directors);
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

    static Genre makeGenre(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("genre_id");
        String name = resultSet.getString("name");
        return new Genre(id, name);
    }

    static Rating makeRating(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("rating_id");
        String name = resultSet.getString("name");
        return new Rating(id, name);
    }

    static Director makeDirector(ResultSet resultSet) throws SQLException {
        Integer id = resultSet.getInt("director_id");
        String name = resultSet.getString("name");
        return new Director(id, name);
    }
}
