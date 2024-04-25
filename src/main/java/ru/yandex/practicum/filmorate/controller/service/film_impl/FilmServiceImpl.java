package ru.yandex.practicum.filmorate.controller.service.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Service
@Qualifier("FilmServiceImpl")
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmServiceImpl(@Qualifier("FilmStorageDao") FilmStorage filmStorage,
                           @Qualifier("UserServiceImpl") UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    @Override
    public Film addLike(int idFilm, int idUser) throws IncorrectIdException {
        search(idFilm);
        userService.search(idUser);
        return filmStorage.addLike(idFilm, idUser);
    }

    @Override
    public Film delLike(int idFilm, int idUser) throws IncorrectIdException {
        search(idFilm);
        userService.search(idUser);
        return filmStorage.delLike(idFilm, idUser);
    }

    @Override
    public List<Film> popFilms(int n) {
        return filmStorage.popFilms(n);
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public Film update(Film film) throws IncorrectIdException {
        search(film.getId());
        return filmStorage.update(film);
    }

    @Override
    public Film delete(Integer id) throws IncorrectIdException {
        search(id);
        return filmStorage.delete(id);
    }

    @Override
    public Film search(int id) throws IncorrectIdException {
        Film film = filmStorage.search(id);
        if (film == null)
            throw new IncorrectIdException("Фильм с ID " + id + " не найден.");
        return film;
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }
}