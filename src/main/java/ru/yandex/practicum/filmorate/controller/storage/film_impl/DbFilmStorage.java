package ru.yandex.practicum.filmorate.controller.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Component("DbFilmStorage")
public class DbFilmStorage implements FilmStorage {
    @Override
    public Film add(Film film) {
        return null;
    }

    @Override
    public Film update(Film film) {
        return null;
    }

    @Override
    public Film delete(Film film) {
        return null;
    }

    @Override
    public Film search(int id) {
        return null;
    }

    @Override
    public List<Film> findAll() {
        return null;
    }
}
