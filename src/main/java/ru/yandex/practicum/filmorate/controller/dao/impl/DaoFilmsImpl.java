package ru.yandex.practicum.filmorate.controller.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoDirectors;
import ru.yandex.practicum.filmorate.controller.dao.DaoFilms;
import ru.yandex.practicum.filmorate.controller.dao.DaoGenres;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Qualifier("DaoFilmsImpl")
public class DaoFilmsImpl implements DaoFilms {
    private final JdbcTemplate jdbcTemplate;
    private final DaoGenres daoGenres;
    private final DaoDirectors daoDirectors;

    @Autowired
    public DaoFilmsImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.daoGenres = new DaoGenresImpl(jdbcTemplate);
        this.daoDirectors = new DaoDirectorsImpl(jdbcTemplate);
    }

    @Override
    public Film add(Film film) {
        Map<String, Object> values = new HashMap<>();
        values.put("film_id", film.getId());
        values.put("name", film.getName());
        values.put("description", film.getDescription());
        values.put("release_date", film.getReleaseDate());
        values.put("duration", film.getDuration());
        values.put("rating_id", film.getMpa().getId());

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("films")
                .usingGeneratedKeyColumns("film_id");

        film.setId(simpleJdbcInsert.executeAndReturnKey(values).intValue());

        return film;
    }

    @Override
    public Film update(Film film) {
        String sqlDate = "UPDATE films " +
                "SET name = ?, description = ?, release_date = ?, duration = ?, rating_id = ? " +
                "WHERE film_id = ?";
        jdbcTemplate.update(sqlDate,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        daoDirectors.updateFilmDirectors(film.getId(), film.getDirectors());
        return daoGenres.updateGenresFilm(film);
    }

    @Override
    public Film delete(Integer id) {
        Film film = search(id);
        String sqlDate = "DELETE FROM films WHERE film_id = ?";
        jdbcTemplate.update(sqlDate, id);
        return film;
    }

    @Override
    public Film search(Integer id) {
        String sql = "SELECT * FROM films WHERE film_id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Film> searchByParam(String query, boolean findByDirector, boolean findByTitle) {
        List<Film> result;

        if (query == null || query.isEmpty()) {
            query = "%";
        } else {
            query = "%" + query + "%";
        }

        String sqlByDirectorOrTitle = "SELECT f.*, COALESCE(l.count, 0) AS likes_count FROM films AS f " +
                "LEFT JOIN (SELECT likes.film_id, count(user_id) AS count " +
                "FROM likes GROUP BY likes.film_id) AS l ON f.film_id = l.film_id " +
                "WHERE f.film_id IN (SELECT fd.film_id FROM directors AS d " +
                "INNER JOIN film_director AS fd ON d.director_id = fd.director_id " +
                "WHERE LOWER(d.name) LIKE LOWER(?)) OR LOWER(f.name) LIKE LOWER(?) " +
                "ORDER BY likes_count DESC";

        String sqlByDirector = "SELECT f.*, COALESCE(l.count, 0) AS likes_count FROM films AS f " +
                "LEFT JOIN (SELECT likes.film_id, count(user_id) AS count " +
                "FROM likes GROUP BY likes.film_id) AS l ON f.film_id = l.film_id " +
                "WHERE f.film_id IN (SELECT fd.film_id FROM directors AS d " +
                "               INNER JOIN film_director AS fd ON d.director_id = fd.director_id " +
                "               WHERE LOWER(d.name) LIKE LOWER(?)) " +
                " ORDER BY likes_count DESC";

        String sqlByTitle = "SELECT f.*, COALESCE(l.count, 0) AS likes_count FROM films AS f " +
                "    LEFT JOIN (SELECT likes.film_id, count(user_id) AS count " +
                "FROM likes GROUP BY likes.film_id) AS l ON f.film_id = l.film_id " +
                "    WHERE LOWER(f.name) LIKE LOWER(?) " +
                "    ORDER BY likes_count DESC";

        if (findByDirector && findByTitle) {
            result = jdbcTemplate.query(sqlByDirectorOrTitle,
                    ((rs, rowNum) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate)), query, query);
        } else if (findByDirector) {
            result = jdbcTemplate.query(sqlByDirector,
                    ((rs, rowNum) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate)), query);
        } else {
            result = jdbcTemplate.query(sqlByTitle,
                    ((rs, rowNum) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate)), query);
        }
        return result;
    }

    @Override
    public List<Film> findAll() {
        String sql = "SELECT * FROM films order by film_id asc";
        try {
            return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Film> getFilmsByDirector(Integer directorId, String sortBy) {
        try {
            String sql;
            if (sortBy.equals("year")) {
                sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.name " +
                        "FROM films AS f " +
                        "JOIN ratings AS r ON f.rating_id = r.rating_id " +
                        "JOIN film_director AS fd ON f.film_id = fd.film_id " +
                        "WHERE fd.director_id = ? " +
                        "ORDER BY release_date ";
                return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), directorId);
            }
            if (sortBy.equals("likes")) {
                sql = "SELECT f.film_id, f.name, f.description, f.release_date, f.duration, f.rating_id, r.name, " +
                        "COUNT(l.user_id) AS likes_count " +
                        "FROM films AS f " +
                        "JOIN ratings AS r ON f.rating_id = r.rating_id " +
                        "JOIN film_director AS fd ON f.film_id = fd.film_id " +
                        "LEFT JOIN likes AS l ON f.film_id = l.film_id " +
                        "WHERE fd.director_id = ? " +
                        "GROUP BY f.film_id " +
                        "ORDER BY likes_count DESC ";
                return jdbcTemplate.query(sql, (rs, num) -> DaoFactoryModel.makeFilm(rs, jdbcTemplate), directorId);
            }
            return new ArrayList<>();
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
