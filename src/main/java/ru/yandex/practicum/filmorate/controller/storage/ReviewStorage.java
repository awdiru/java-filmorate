package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewStorage {

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
     * @param review обновляемый отзыв
     * @return обновленный отзыв
     */
    Review updateReview(Review review);

    /**
     * Удалить отзыв
     *
     * @param id идентификатор удаляемого отзыва
     */
    void deleteReview(int id);

    /**
     * Добавить like отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     */
    void addLike(int reviewId, int userId);

    /**
     * Удалить like с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     */
    void deleteLike(int reviewId, int userId);

    /**
     * Добавить dislike отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     */
    void addDislike(int reviewId, int userId);

    /**
     * Удалить dislike с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     */
    void deleteDislike(int reviewId, int userId);
}
