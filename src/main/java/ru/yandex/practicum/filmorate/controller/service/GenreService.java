package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreService {
    /**
     * Получить название жанра по идентификатору
     * @param id идентификатор жанра
     * @return жанр
     */
    Genre getById(Integer id) throws IncorrectIdException;

    /**
     * Получить список всех жанров
     * @return список всех жанров
     */
    List<Genre> findAllGenres();
}
