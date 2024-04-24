package ru.yandex.practicum.filmorate.controller.storage.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Component
@Primary
public class DbFilmStorage implements FilmStorage {

    private static JdbcTemplate jdbcTemplate;

    @Autowired
    public DbFilmStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film add(Film film) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        Integer id = simpleJdbcInsert.executeAndReturnKey(toMap(film)).intValue();
        film.setId(id);

        if (!film.getLikes().isEmpty()) {
            String sqlLikes = "insert into likes (film_id, user_id) values (?, ?)";
            Integer uId = film.getId();

            for (Integer fId : film.getLikes()) {
                jdbcTemplate.update(sqlLikes, fId, uId);
            }
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlDate = "update films set " +
                "name = ?, description = ?, release_date = ?, duration = ?, rating_id = ?" +
                "where id = ?";

        jdbcTemplate.update(sqlDate,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId(),
                film.getId());

        return film;
    }

    @Override
    public Film delete(Film film) {
        String sqlDate = "delete from films where film_id = ?";
        jdbcTemplate.update(sqlDate, film.getId());
        return film;
    }

    @Override
    public Film search(int id) {
        String sql = "select * from films where film_id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, num) -> makeFilm(rs), id);
    }

    @Override
    public List<Film> findAll() {
        String sql = "select * from films";
        return jdbcTemplate.query(sql, (rs, num) -> makeFilm(rs));
    }

    private Film makeFilm(ResultSet resultSet) throws SQLException {
        Integer filmId = resultSet.getInt("film_id");
        String description = resultSet.getString("description");
        String name = resultSet.getString("name");
        LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
        Integer duration = resultSet.getInt("duration");
        Integer ratingId = resultSet.getInt("rating_id");

        String sqlLikes = "select user_id from likes where film_id = ?";
        Set<Integer> likes = new HashSet<>(jdbcTemplate.query(sqlLikes, (rs, rowNum) -> rs.getInt("user_id"), filmId));

        return new Film(filmId, description, name, releaseDate, duration, ratingId, likes);
    }

    private Map<String, Object> toMap(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("film_id", film.getId());
        values.put("description", film.getDescription());
        values.put("name", film.getName());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("rating_id", film.getRatingId());
        return values;
    }
}
