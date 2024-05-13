package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DaoDirectors {
    /**
     * Создать режиссера
     *
     * @param director объект-режиссер
     */
    Director createDirector(Director director);

    /**
     * Добавление режиссера фильму
     *
     * @param filmId id фильма
     * @param directors список режиссеров
     */
    void addFilmDirectors(Integer filmId, List<Director> directors);

    /**
     * Обновить режиссера
     *
     * @param director объект-режиссер
     */
    Director updateDirector(Director director);

    /**
     * Обновить режиссеров для фильма
     *
     * @param filmId id фильма
     * @param directors список режиссеров
     */
    void updateFilmDirectors(Integer filmId, List<Director> directors);

    /**
     * Получить имя режиссера по идентификатору
     *
     * @param id идентификатор режиссера
     * @return жанр
     */
    Director getById(Integer id);

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
    void deleteDirector(Integer id);
}
