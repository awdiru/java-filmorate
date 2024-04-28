package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    /**
     * Добавить друга
     *
     * @param idUser   id пользователя
     * @param idFriend id друга
     * @return обновленный пользователь
     * @throws IncorrectIdException некорректный идентификатор
     */
    User addFriend(int idUser, int idFriend) throws IncorrectIdException;

    /**
     * Удалить друга
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     * @return обновленный пользователь
     */
    User deleteFriend(int idUser, int idFriend);

    /**
     * Список друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список друзей
     */
    List<User> getFriends(int id);

    /**
     * Список общих друзей
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     * @return список общих друзей
     */
    List<User> getMutualFriends(int idUser, int idFriend);

    /**
     * Добавить пользователя
     *
     * @param user добавляемый пользователь
     * @return добавленный пользователь
     */
    User add(User user);

    /**
     * Обновить пользователя
     *
     * @param user обновляемый пользователь
     * @return обновленный пользователь
     */
    User update(User user);

    /**
     * Удалить пользователя
     *
     * @param id идентификатор удаляемого пользователя
     * @return удаленный пользователь
     */
    User delete(Integer id);

    /**
     * Найти пользователя
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь или null
     */
    User search(int id);

    /**
     * Вернуть список всех пользователей
     *
     * @return список всех пользователей
     */
    List<User> findAll();
}
