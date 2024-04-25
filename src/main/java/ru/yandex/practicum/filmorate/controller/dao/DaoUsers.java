package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface DaoUsers {
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
