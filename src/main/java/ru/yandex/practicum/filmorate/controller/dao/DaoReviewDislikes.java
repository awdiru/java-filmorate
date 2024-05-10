package ru.yandex.practicum.filmorate.controller.dao;

import java.util.List;

public interface DaoReviewDislikes {

    /**
     * Вернуть id пользователей поставивших dislike отзыву
     *
     * @param reviewId идентификатор отзыва
     * @return список идентификаторов пользователей
     */
    List<Integer> getDislikes(int reviewId);

    /**
     * Добавить dislike отзыву
     *
     * @param reviewId идентификатор отзыва
     * @param userId идентификатор пользователя
     */
    void addDislike(int reviewId, int userId);

    /**
     * Удалить dislike с отзыва
     *
     * @param reviewId идентификатор отзыва
     * @param userId идентификатор пользователя
     */
    void deleteDislike(int reviewId, int userId);
}
