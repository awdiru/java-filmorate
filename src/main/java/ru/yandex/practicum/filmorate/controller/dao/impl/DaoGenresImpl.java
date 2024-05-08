package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoGenres;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("DaoGenresImpl")
public class DaoGenresImpl implements DaoGenres {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoGenresImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addGenreFilm(Integer idFilm, Integer idGenre) {
        String sql = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, idFilm, idGenre);
    }

    @Override
    public Genre getById(Integer id) {
        String sql = "SELECT * FROM genres WHERE genre_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoFactoryModel.makeGenre(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Genre> findAllGenres() {
        String sql = "SELECT * FROM genres";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeGenre(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Genre> getGenresFilm(Integer idFilm) {
        String sql = "SELECT * FROM genres WHERE genre_id IN " +
                "(SELECT genre_id FROM film_genre WHERE film_id = ?)";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeGenre(rs), idFilm);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
