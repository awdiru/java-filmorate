package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface DaoReviews {

    /**
     * Добавить отзыв
     *
     * @param review добавляемый отзыв
     * @return добавленный отзыв
     */
    Review addReview(Review review);

    /**
     * Найти отзыв
     *
     * @param id идентификатор отзыва
     * @return найденный отзыв или null
     */
    Review getReview(int id);

    /**
     * Вернуть список отзывов фильма
     *
     * @param filmId идентификатор фильма
     * @param count  количество возвращаемых отзывов
     * @return список отзывов фильма
     */
    List<Review> getFilmReviews(int filmId, int count);

    /**
     * Вернуть список всех отзывов
     *
     * @return список всех отзывов
     */
    List<Review> getAllReviews();

    /**
     * Обновить отзыв
     *
     * @param review обновлямый отзыв
     * @return обновленный отзыв
     */
    Review updateReview(Review review);

    /**
     * Удалить отзыв
     *
     * @param id идентификатор удаляемого отзыва
     */
    void deleteReview(int id);
}
