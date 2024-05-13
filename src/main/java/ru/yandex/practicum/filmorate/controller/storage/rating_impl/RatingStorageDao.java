package ru.yandex.practicum.filmorate.controller.storage.rating_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoRating;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoRatingImpl;
import ru.yandex.practicum.filmorate.controller.storage.RatingStorage;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Component
@Qualifier("RatingStorageDao")
public class RatingStorageDao implements RatingStorage {
    private final DaoRating daoRating;

    @Autowired
    public RatingStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoRating = new DaoRatingImpl(jdbcTemplate);
    }

    @Override
    public Rating getById(Integer id) {
        return daoRating.getById(id);
    }

    @Override
    public List<Rating> findAllRatings() {
        return daoRating.findAll();
    }
}
