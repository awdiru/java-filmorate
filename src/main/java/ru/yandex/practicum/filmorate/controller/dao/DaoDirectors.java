package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DaoDirectors {
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
    Director updateDirector(Director director);

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
     * Получить список режиссеров фильма
     *
     * @param idFilm идентификатор фильма
     * @return список всех режиссеров фильма
     */
    List<Director> getDirectorsFilm(Integer idFilm);

    /**
     * Удаление режиссера
     *
     * @param id идентификатор режиссера
     */
    void deleteDirector(Integer id);
}
