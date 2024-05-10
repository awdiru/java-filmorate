package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviews;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("DaoReviewsImpl")
@AllArgsConstructor
public class DaoReviewsImpl implements DaoReviews {

    private JdbcTemplate jdbcTemplate;

    @Override
    public Review addReview(Review review) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reviews")
                .usingGeneratedKeyColumns("review_id");
        int newId = simpleJdbcInsert.executeAndReturnKey(getMap(review)).intValue();
        review.setReviewId(newId);
        System.out.println("id: " + newId);
        return review;
    }

    @Override
    public Review getReview(int id) {
        String sql = "select r.*, " +
                "coalesce(l.likes, 0) - coalesce(d.dislikes, 0) as useful " +
                "from reviews r " +
                "left join (select review_id, " +
                "count(user_id) as likes " +
                "from review_likes " +
                "group by review_id) as l on l.review_id = r.review_id " +
                "left join (select review_id, " +
                "count(user_id) as dislikes " +
                "from review_dislikes " +
                "group by review_id) as d on d.review_id = r.review_id " +
                "where r.review_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, reviewRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Review> getFilmReviews(int filmId, int count) {
        String sql = "select r.*, " +
                "coalesce(l.likes, 0) - coalesce(d.dislikes, 0) as useful " +
                "from reviews r " +
                "left join (select review_id, " +
                "count(user_id) as likes " +
                "from review_likes " +
                "group by review_id) as l on l.review_id = r.review_id " +
                "left join (select review_id, " +
                "count(user_id) as dislikes " +
                "from review_dislikes " +
                "group by review_id) as d on d.review_id = r.review_id " +
                "where r.film_id = ? " +
                "order by useful desc, r.review_id asc " +
                "limit ?";
        return jdbcTemplate.query(sql, reviewRowMapper(), filmId, count);
    }

    @Override
    public List<Review> getAllReviews() {
        String sql = "select r.*, " +
                "coalesce(l.likes, 0) - coalesce(d.dislikes, 0) as useful " +
                "from reviews r " +
                "left join (select review_id, " +
                "count(user_id) as likes " +
                "from review_likes " +
                "group by review_id) as l on l.review_id = r.review_id " +
                "left join (select review_id, " +
                "count(user_id) as dislikes " +
                "from review_dislikes " +
                "group by review_id) as d on d.review_id = r.review_id " +
                "order by useful desc, r.review_id asc";
        return jdbcTemplate.query(sql, reviewRowMapper());
    }

    @Override
    public Review updateReview(Review review) {
        String sql = "update reviews set content = ?, is_positive = ? where review_id = ?";
        jdbcTemplate.update(sql, review.getContent(), review.getIsPositive(), review.getReviewId());
        return getReview(review.getReviewId());
    }

    @Override
    public void deleteReview(int id) {
        String sql = "delete from reviews where review_id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Review> reviewRowMapper() {
        return (rs, rowNum) -> new Review(rs.getInt("REVIEW_ID"),
                rs.getString("CONTENT"),
                rs.getBoolean("IS_POSITIVE"),
                rs.getInt("USER_ID"),
                rs.getInt("FILM_ID"),
                rs.getInt("USEFUL"));
    }

    private Map<String, Object> getMap(Review review) {
        Map<String, Object> values = new HashMap<>();
        values.put("CONTENT", review.getContent());
        values.put("IS_POSITIVE", review.getIsPositive());
        values.put("USER_ID", review.getUserId());
        values.put("FILM_ID", review.getFilmId());
        return values;
    }
}
