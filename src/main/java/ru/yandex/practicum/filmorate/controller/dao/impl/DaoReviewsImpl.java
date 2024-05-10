package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviews;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("DaoReviewsImpl")
@AllArgsConstructor
public class DaoReviewsImpl implements DaoReviews {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Review addReview(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("review_id");
        int newId = simpleJdbcInsert.executeAndReturnKey(getMap(review)).intValue();
        review.setReviewId(newId);
        return review;
    }

    @Override
    public Review getReview(int id) {
        String sql = "SELECT r.*, " +
                "COALESCE(l.likes, 0) - COALESCE(d.dislikes, 0) as useful " +
                "FROM reviews r " +
                "LEFT JOIN (SELECT review_id, " +
                "COUNT(user_id) as likes " +
                "FROM review_likes " +
                "GROUP BY review_id) as l ON l.review_id = r.review_id " +
                "LEFT JOIN (SELECT review_id, " +
                "COUNT(user_id) as dislikes " +
                "FROM review_dislikes " +
                "GROUP BY review_id) as d ON d.review_id = r.review_id " +
                "WHERE r.review_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, reviewRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Review> getFilmReviews(int filmId, int count) {
        String sql = "SELECT r.*, " +
                "COALESCE(l.likes, 0) - COALESCE(d.dislikes, 0) as useful " +
                "FROM reviews r " +
                "LEFT JOIN (select review_id, " +
                "COUNT(user_id) as likes " +
                "FROM review_likes " +
                "GROUP BY review_id) as l ON l.review_id = r.review_id " +
                "LEFT JOIN (SELECT review_id, " +
                "COUNT(user_id) as dislikes " +
                "FROM review_dislikes " +
                "GROUP BY review_id) as d ON d.review_id = r.review_id " +
                "WHERE r.film_id = ? " +
                "ORDER BY useful DESC, r.review_id ASC " +
                "LIMIT ?";
        try {
            return jdbcTemplate.query(sql, reviewRowMapper(), filmId, count);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Review> getAllReviews() {
        String sql = "SELECT r.*, " +
                "COALESCE(l.likes, 0) - COALESCE(d.dislikes, 0) as useful " +
                "FROM reviews r " +
                "LEFT JOIN (SELECT review_id, " +
                "COUNT(user_id) as likes " +
                "FROM review_likes " +
                "GROUP BY review_id) as l ON l.review_id = r.review_id " +
                "LEFT JOIN (select review_id, " +
                "COUNT(user_id) as dislikes " +
                "FROM review_dislikes " +
                "GROUP BY review_id) as d ON d.review_id = r.review_id " +
                "ORDER BY useful DESC, r.review_id ASC";
        try {
            return jdbcTemplate.query(sql, reviewRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Review updateReview(Review review) {
        String sql = "UPDATE reviews SET content = ?, is_positive = ? WHERE review_id = ?";
        try {
            int updatedRow = jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(), review.getReviewId());
            if (updatedRow > 0) {
                return getReview(review.getReviewId());
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public void deleteReview(int id) {
        String sql = "DELETE FROM reviews WHERE review_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void addLike(int reviewId, int userId) {
        String sql = "INSERT INTO review_likes VALUES (?,?)";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        String sql = "DELETE FROM review_likes WHERE review_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    @Override
    public void addDislike(int reviewId, int userId) {
        String sql = "INSERT INTO review_dislikes VALUES (?,?)";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        String sql = "DELETE FROM review_dislikes WHERE review_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    private RowMapper<Review> reviewRowMapper() {
        return (rs, rowNum) -> new Review(rs.getInt("review_id"),
                rs.getString("content"),
                rs.getBoolean("is_positive"),
                rs.getInt("user_id"),
                rs.getInt("film_id"),
                rs.getInt("useful"));
    }

    private Map<String, Object> getMap(Review review) {
        Map<String, Object> values = new HashMap<>();
        values.put("content", review.getContent());
        values.put("is_positive", review.getIsPositive());
        values.put("user_id", review.getUserId());
        values.put("film_id", review.getFilmId());
        return values;
    }
}
