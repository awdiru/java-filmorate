package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface DaoRating {
    /**
     * Найти рейтинг по идентификатору
     * @param id идентификатор
     * @return рейтинг
     */
    Rating getById(Integer id);

    /**
     * Вернуть список всех рейтингов
     * @return список рейтингов
     */
    List<Rating> findAll();
}
