package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoDirectors;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Director;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("DaoDirectorsImpl")
public class DaoDirectorsImpl implements DaoDirectors {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DaoDirectorsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Director addDirectorFilm(Director director) {
        String sql = "INSERT INTO directors (name) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, director.getName());
            return ps;
        }, keyHolder);

        Integer id = (Integer) keyHolder.getKey();
        return getById(id);
    }

    @Override
    public Director updateDirector(Director director) {
        String sql = "UPDATE directors SET name = ? WHERE director_id = ?";
        try {
            int updatedRow = jdbcTemplate.update(sql, director.getName(), director.getId());
            if(updatedRow > 0) {
                return director;
            }
            return null;
        } catch (DataAccessException e) {
            return null;
        }
    }

    @Override
    public Director getById(Integer id) {
        String sql = "SELECT * FROM directors WHERE director_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoFactoryModel.makeDirector(rs), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Director> findAllDirectors() {
        String sql = "SELECT * FROM directors";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeDirector(rs));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Director> getDirectorsFilm(Integer idFilm) {
        String sql = "SELECT * FROM directors WHERE director_id IN " +
                "(SELECT director_id FROM film_director WHERE film_id = ?)";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeDirector(rs), idFilm);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void deleteDirector(Integer id) {
        String sql ="DELETE FROM directors WHERE director_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
