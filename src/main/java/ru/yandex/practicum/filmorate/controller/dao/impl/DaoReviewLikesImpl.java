package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviewLikes;

import java.util.List;

@Component
@AllArgsConstructor
@Qualifier("DaoReviewLikesImpl")
public class DaoReviewLikesImpl implements DaoReviewLikes {

    JdbcTemplate jdbcTemplate;

    @Override
    public List<Integer> getLikes(int reviewId) {
        String sql = "select user_id " +
                "from review_likes " +
                "where review_id = ?";
        List<Integer> reviewLikes = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"));
        return reviewLikes;
    }

    @Override
    public void addLike(int reviewId, int userId) {
        String sql = "insert into review_likes values (?,?)";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        String sql = "delete from review_likes where review_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, reviewId, userId);
    }
}
