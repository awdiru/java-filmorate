package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

public interface RatingStorage {
    /**
     * Получить название рейтинга по идентификатору
     * @param id идентификатор рейтинга
     * @return жанр
     */
    Rating getById(Integer id) throws IncorrectIdException;

    /**
     * Получить список всех рейтингов
     * @return список всех рейтингов
     */
    List<Rating> findAllRatings();
}
