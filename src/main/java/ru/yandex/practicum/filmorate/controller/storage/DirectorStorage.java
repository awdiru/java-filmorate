package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorStorage {
    /**
     * Создать режиссера
     *
     * @param director объект-режиссер
     */
    Director createDirector(Director director);

    /**
     * Обновить режиссера
     *
     * @param director объект-режиссер
     */
    Director updateDirector(Director director);

    /**
     * Получить имя режиссера по идентификатору
     *
     * @param id идентификатор решиссера
     * @return имя режиссера
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