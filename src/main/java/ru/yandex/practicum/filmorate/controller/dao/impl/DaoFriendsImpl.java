package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFriends;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component
@Qualifier("DaoFriendsImpl")
public class DaoFriendsImpl implements DaoFriends {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoFriendsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(int idUser, int idFriend) {
        String sql = "insert into friends (user_id, friend_id) values (?, ?)";
        jdbcTemplate.update(sql, idUser, idFriend);
        String sql1 = "insert into friends (user_id, friend_id) values (?, ?)";
        jdbcTemplate.update(sql1, idFriend, idUser);
    }

    @Override
    public void deleteFriend(int idUser, int idFriend) {
        String sql = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql, idUser, idFriend);
        String sql1 = "delete from friends where user_id = ? and friend_id = ?";
        jdbcTemplate.update(sql1, idFriend, idUser);
    }

    @Override
    public List<User> findAllFriendsUser(int idUser) {
        try {
            String sql = "SELECT * " +
                    "FROM  users " +
                    "WHERE user_id IN (SELECT friend_id FROM friends WHERE user_id = ?);";
            return jdbcTemplate.query(sql, (rs, rowNum) -> DaoExample.makeUser(rs, jdbcTemplate), idUser);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> getMutualFriends(int idUser, int idFriend) {
        try {
            String sql = "SELECT * FROM users " +
                    "WHERE user_id IN (SELECT friend_id FROM friends WHERE user_id = ?) " +
                    "AND user_id IN (SELECT friend_id FROM friends WHERE user_id = ?)";
            return jdbcTemplate.query(sql, (rs, rowNum) -> DaoExample.makeUser(rs, jdbcTemplate), idUser, idFriend);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
