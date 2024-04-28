package ru.yandex.practicum.filmorate.controller.storage.genre_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoGenres;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoGenresImpl;
import ru.yandex.practicum.filmorate.controller.storage.GenreStorage;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Component
@Qualifier("GenreStorageDao")
public class GenreStorageDao implements GenreStorage {
    private final DaoGenres daoGenres;

    @Autowired
    public GenreStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoGenres = new DaoGenresImpl(jdbcTemplate);
    }

    @Override
    public Genre getById(Integer id) {
        return daoGenres.getById(id);
    }

    @Override
    public List<Genre> findAllGenres() {
        return daoGenres.findAllGenres();
    }
}
