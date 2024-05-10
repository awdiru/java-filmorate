package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {

    /**
     * Добавить отзыв
     *
     * @param review отзыв
     * @return добавленный отзыв с новым id
     * @throws IncorrectIdException
     */
    Review addReview(Review review) throws IncorrectIdException;

    /**
     * Найти отзыв
     *
     * @param id идентификатор отзыва
     * @return найденный отзыв или null
     * @throws IncorrectIdException
     */
    Review getReview(int id) throws IncorrectIdException;

    /**
     * Вернуть список отзывов фильма
     *
     * @param filmId идентификатор фильма
     * @param count  количество возвращаемых отзывов
     * @return список отзывов фильма
     * @throws IncorrectIdException
     */
    List<Review> getFilmReviews(int filmId, int count) throws IncorrectIdException;

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
     * @throws IncorrectIdException
     */
    Review updateReview(Review review) throws IncorrectIdException;

    /**
     * Удалить отзыв
     *
     * @param id идентификатор удаляемого отзыва
     * @throws IncorrectIdException
     */
    void deleteReview(int id) throws IncorrectIdException;

    /**
     * Добавить like отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     * @throws IncorrectIdException
     */
    void addLike(int reviewId, int userId) throws IncorrectIdException;

    /**
     * Удалить like с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     * @throws IncorrectIdException
     */
    void deleteLike(int reviewId, int userId) throws IncorrectIdException;

    /**
     * Добавить dislike отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     * @throws IncorrectIdException
     */
    void addDislike(int reviewId, int userId) throws IncorrectIdException;

    /**
     * Удалить dislike с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId   идентификатор пользователя
     * @throws IncorrectIdException
     */
    void deleteDislike(int reviewId, int userId) throws IncorrectIdException;
}
