package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoLikes;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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

        try {
            String sql = "SELECT * FROM films WHERE film_id IN ( " +
                    "SELECT film_id FROM likes GROUP BY film_id ORDER BY count(user_id) LIMIT ?)";
            List<Film> films = jdbcTemplate.query(sql, (rs, rowNum) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), n);

            if (films.isEmpty()) {
                String sqlAll = "SELECT * FROM films ORDER BY name LIMIT ?";
                films = jdbcTemplate.query(sqlAll, (rs, rowNum) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), n);
            }
            return films;
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Map<Integer, Set<Integer>> getAllUserIdWithLikes() {
        String sql = "SELECT * FROM likes";
        List<Like> likes;
        try {
            likes = jdbcTemplate.query(sql, (rs, rowNum) -> makeLike(rs));
        } catch (EmptyResultDataAccessException e) {
            return new HashMap<>();
        }

        Map<Integer, Set<Integer>> userIdWithLikes = new HashMap<>();
        for (Like like : likes) {
            if (!userIdWithLikes.containsKey(like.idUser))
                userIdWithLikes.put(like.idUser, new HashSet<>());
            userIdWithLikes.get(like.idUser).add(like.idFilm);
        }
        return userIdWithLikes;
    }

    private Like makeLike(ResultSet rs) throws SQLException {
        Integer idUser = rs.getInt("user_id");
        Integer idFilm = rs.getInt("film_id");
        return new Like(idUser, idFilm);
    }

    @AllArgsConstructor
    private class Like {
        private Integer idUser;
        private Integer idFilm;
    }
}
