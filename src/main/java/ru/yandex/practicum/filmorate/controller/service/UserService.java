package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService {
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
     * @throws IncorrectIdException некорректный идентификатор
     */
    User deleteFriend(int idUser, int idFriend) throws IncorrectIdException;

    /**
     * Список друзей пользователя
     *
     * @param id идентификатор пользователя
     * @return список друзей
     * @throws IncorrectIdException некорректный идентификатор
     */
    List<User> getFriends(int id) throws IncorrectIdException;

    /**
     * Список общих друзей
     *
     * @param idUser   идентификатор пользователя
     * @param idFriend идентификатор друга
     * @return список общих друзей
     * @throws IncorrectIdException некорректный идентификатор
     */
    List<User> getMutualFriends(int idUser, int idFriend) throws IncorrectIdException;

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
    User update(User user) throws IncorrectIdException;

    /**
     * Удалить пользователя
     *
     * @param id идентификатор удаляемого пользователя
     * @return удаленный пользователь
     */
    User delete(Integer id) throws IncorrectIdException;

    /**
     * Найти пользователя
     *
     * @param id идентификатор пользователя
     * @return найденный пользователь или null
     */
    User search(int id) throws IncorrectIdException;

    /**
     * Лента событий пользователя
     *
     * @param userId идентификатор пользователя
     * @return лента событий
     * @throws IncorrectIdException
     */

    List<Event> getFeed(int userId) throws IncorrectIdException;

    /**
     * Вернуть список всех пользователей
     *
     * @return список всех пользователей
     */

    List<User> findAll();

    /**
     * Показать рекомендации по фильмам для пользователя
     *
     * @param id идентификатор пользователя
     * @return список рекомендованных фильмов
     */
    List<Film> getRecommendations(int id) throws IncorrectIdException;
}
