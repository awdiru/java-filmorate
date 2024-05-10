package ru.yandex.practicum.filmorate.controller.dao;

import java.util.List;

public interface DaoReviewLikes {

    /**
     * Вернуть id пользователей поставивших like отзыву
     *
     * @param reviewId идентификатор отзыва
     * @return список идентификаторов пользователей
     */
    List<Integer> getLikes(int reviewId);

    /**
     * Добавить like отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId идентификатор пользователя
     */
    void addLike(int reviewId, int userId);

    /**
     * Удалить like с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId идентификатор пользователя
     */
    void deleteLike(int reviewId, int userId);
}
