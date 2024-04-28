package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface DaoFriends {
    /**
     * Добавить друга
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     */
    void addFriend(int idUser, int idFriend);

    /**
     * Удалить друга
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     */
    void deleteFriend(int idUser, int idFriend);

    /**
     * Вернуть список всех друзей пользователя
     *
     * @param idUser идентификатор пользователя
     * @return список друзей пользователя
     */
    List<User> findAllFriendsUser(int idUser);

    /**
     * Вернуть список общих друзей пользователей
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     * @return список друзей друга
     */
    List<User> getMutualFriends(int idUser, int idFriend);
}
