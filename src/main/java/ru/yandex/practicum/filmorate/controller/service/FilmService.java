package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.controller.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService extends FilmStorage {
    Film addLike(int idFilm, int idUser) throws IncorrectIdException;

    Film delLike(int idFilm, int idUser) throws IncorrectIdException;

    List<Film> popFilms(int count);
}
