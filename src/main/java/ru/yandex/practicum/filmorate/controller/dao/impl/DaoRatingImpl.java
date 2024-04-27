package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.dao.DaoRating;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public class DaoRatingImpl implements DaoRating {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoRatingImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Rating getById(Integer id) {
        String sql = "SELECT * FROM ratings WHERE rating_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoFactoryModel.makeRating(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Rating> findAll() {
        String sql = "SELECT * FROM ratings";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeRating(rs));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
