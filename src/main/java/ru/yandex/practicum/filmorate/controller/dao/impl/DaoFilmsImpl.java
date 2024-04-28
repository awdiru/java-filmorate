package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFilms;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
@Qualifier("DaoFilmsImpl")
public class DaoFilmsImpl implements DaoFilms {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoFilmsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film add(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("film_id", film.getId());
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("rating_id", film.getMpa().getId());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        film.setId(simpleJdbcInsert.executeAndReturnKey(values).intValue());

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlDate = "UPDATE films " +
                "SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlDate,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());
        return film;
    }

    @Override
    public Film delete(Integer id) {
        Film film = search(id);
        String sqlDate = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(sqlDate, id);
        return film;
    }

    @Override
    public Film search(Integer id) {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM films";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
