package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorService {
    /**
     * Создать режиссера
     *
     * @param director объект-режиссер
     */
    Director addDirectorFilm(Director director);

    /**
     * Обновить режиссера
     *
     * @param director объект-режиссер
     */
    Director updateDirector(Director director) throws IncorrectIdException;

    /**
     * Получить имя режиссера по идентификатору
     *
     * @param id идентификатор режиссера
     * @return имя режиссера
     */
    Director getById(Integer id) throws IncorrectIdException;

    /**
     * Получить список всех режиссеров
     *
     * @return список всех режиссеров
     */
    List<Director> findAllDirectors();

    /**
     * Удаление режиссера
     *
     * @param id идентификатор режиссера
     */
    void deleteDirector(Integer id) throws IncorrectIdException;
}
