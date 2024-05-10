package ru.yandex.practicum.filmorate.controller.dao.impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviewDislikes;

import java.util.List;

@Component
@AllArgsConstructor()
@Qualifier("DaoReviewDislikesImpl")
public class DaoReviewDislikesImpl implements DaoReviewDislikes {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Integer> getDislikes(int reviewId) {
        String sql = "select user_id " +
                "from review_dislikes " +
                "where review_id = ?";
        List<Integer> reviewLikes = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getInt("user_id"), reviewId);
        return reviewLikes;
    }

    @Override
    public void addDislike(int reviewId, int userId) {
        String sql = "insert into review_dislikes values (?,?)";
        jdbcTemplate.update(sql, reviewId, userId);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        String sql = "delete from review_dislikes where review_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, reviewId, userId);
    }
}
