package ru.yandex.practicum.filmorate.controller.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmServiceImpl(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    @Override
    public Film addLike(int idFilm, int idUser) throws IncorrectIdException {
        Film film = filmStorage.search(idFilm);
        User user = userService.search(idUser);

        if (film == null) throw new IncorrectIdException("Фильм с ID " + idFilm + " не найден.");
        if (user == null) throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден.");

        if (film.getLikes() == null) film.setLikes(new HashSet<>());

        Set<Integer> likes = film.getLikes();
        likes.add(user.getId());
        return film;
    }

    @Override
    public Film delLike(int idFilm, int idUser) throws IncorrectIdException {
        Film film = filmStorage.search(idFilm);
        User user = userService.search(idUser);

        if (film == null) throw new IncorrectIdException("Фильм с ID " + idFilm + " не найден.");
        if (user == null) throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден.");

        Set<Integer> likes = film.getLikes();
        if (likes != null) likes.remove(user.getId());
        return film;
    }

    @Override
    public List<Film> popFilms(int count) {
        List<Film> allFilms = filmStorage.findAll();
        if (count >= allFilms.size()) count = allFilms.size();
        if (count < 0) count = 0;

        return filmStorage.findAll().stream()
                .filter(film -> !film.getLikes().isEmpty())
                .sorted((Film film1, Film film2) -> Integer.compare(film2.getLikes().size(), film1.getLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public Film delete(Film film) {
        return filmStorage.delete(film);
    }

    @Override
    public Film search(int id) {
        return filmStorage.search(id);
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }
}