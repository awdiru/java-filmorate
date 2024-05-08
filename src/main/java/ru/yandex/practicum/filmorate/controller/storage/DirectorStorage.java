package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

public interface DirectorStorage {
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
}