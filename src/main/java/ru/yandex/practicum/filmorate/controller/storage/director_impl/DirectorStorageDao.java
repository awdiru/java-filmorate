package ru.yandex.practicum.filmorate.controller.storage.director_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoDirectors;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoDirectorsImpl;
import ru.yandex.practicum.filmorate.controller.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Component
@Qualifier("DirectorStorageDao")
public class DirectorStorageDao implements DirectorStorage {
    private final DaoDirectors daoDirectors;

    @Autowired
    public DirectorStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoDirectors = new DaoDirectorsImpl(jdbcTemplate);
    }

    @Override
    public Director getById(Integer id) {
        return daoDirectors.getById(id);
    }

    @Override
    public List<Director> findAllDirectors() {
        return daoDirectors.findAllDirectors();
    }
}