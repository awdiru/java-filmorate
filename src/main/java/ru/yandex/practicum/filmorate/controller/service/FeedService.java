package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

public interface FeedService {

    /**
     * Вернуть ленту событий пользователя
     *
     * @param userId идентификатор пользователя
     * @return лента событий
     */
    List<Event> getFeed(int userId);

    /**
     * Добавить событие "добавить друга"
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор пользователя
     */
    void addAddFriendEvent(int userId, int friendId);

    /**
     * Добавить событие "удалить друга"
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор пользователя
     */
    void addRemoveFriendEvent(int userId, int friendId);

    /**
     * Добавить событие "добавить лайк"
     *
     * @param userId   идентификатор пользователя
     * @param entityId идентификатор сущности, с которой произошло событие
     */
    void addAddLikeEvent(int userId, int entityId);

    /**
     * Добавить событие "удалить лайк"
     *
     * @param userId   идентификатор пользователя
     * @param entityId идентификатор сущности, с которой произошло событие
     */
    void addRemoveLikeEvent(int userId, int entityId);

    /**
     * Добавить событие "добавить отзыв"
     *
     * @param userId   идентификатор пользователя
     * @param reviewId идентификатор отзыва
     */
    void addAddReviewEvent(int userId, int reviewId);

    /**
     * Добавить событие "удалить отзыв"
     *
     * @param userId   идентификатор пользователя
     * @param reviewId идентификатор отзыва
     */
    void addRemoveReviewEvent(int userId, int reviewId);

    /**
     * Добавить событие "обновить отзыв"
     *
     * @param userId   идентификатор пользователя
     * @param reviewId идентификатор отзыва
     */
    void addUpdateReviewEvent(int userId, int reviewId);

}
