package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFriends;
import ru.yandex.practicum.filmorate.controller.dao.DaoUsers;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Qualifier("DaoUsersImpl")
public class DaoUsersImpl implements DaoUsers {
    private final JdbcTemplate jdbcTemplate;
    private final DaoFriends daoFriends;

    @Autowired
    public DaoUsersImpl(JdbcTemplate jdbcTemplate, @Qualifier("DaoFriendsImpl") DaoFriends daoFriends) {
        this.jdbcTemplate = jdbcTemplate;
        this.daoFriends = daoFriends;
    }

    @Override
    public User add(User user) {
        Map<String, Object> values = new HashMap<>();
        values.put("user_id", user.getId());
        values.put("email", user.getEmail());
        values.put("login", user.getLogin());
        values.put("name", user.getName());
        values.put("birthday", user.getBirthday());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("user_id");

        user.setId(simpleJdbcInsert.executeAndReturnKey(values).intValue());
        return user;
    }

    @Override
    public User update(User user) {
        String sqlDate = "UPDATE users " +
                "SET email = ?, login = ?, name = ?, birthday = ? " +
                "WHERE user_id = ?";
        jdbcTemplate.update(sqlDate,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId());

        return user;
    }

    @Override
    public User delete(Integer id) {
        User user = search(id);
        String sqlDate = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sqlDate, id);
        return user;
    }

    @Override
    public User search(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoExample.makeUser(rs, jdbcTemplate), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoExample.makeUser(rs, jdbcTemplate));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


}
