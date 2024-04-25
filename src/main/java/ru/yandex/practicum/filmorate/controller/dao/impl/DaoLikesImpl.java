package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoLikes;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("DaoLikesImpl")
public class DaoLikesImpl implements DaoLikes {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoLikesImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(int idFilm, int idUser) {
        String sql = "INSERT INTO likes (film_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, idFilm, idUser);
    }

    @Override
    public void delLike(int idFilm, int idUser) {
        String sql = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, idFilm, idUser);
    }

    @Override
    public List<Integer> findAllIdUsersLikesFilm(int idFilm) {
        String sql = "SELECT user_id FROM likes WHERE film_id = ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), idFilm);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Film> getPop(int n) {
        String sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id " +
                "FROM likes l " +
                "JOIN films f ON f.film_id = l.film_is " +
                "GROUP BY l.film_id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?";
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> DaoExample.makeFilm(rs, jdbcTemplate), n);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
