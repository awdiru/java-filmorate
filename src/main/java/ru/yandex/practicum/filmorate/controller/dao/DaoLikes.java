package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DaoLikes {
    /**
     * Добавить лайк фильму
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     */
    void addLike(int idFilm, int idUser);

    /**
     * Удалить лайк у фильма
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     */
    void delLike(int idFilm, int idUser);

    /**
     * Вернуть список пользователей, что поставили лайк фильму
     *
     * @param idFilm идентификатор фильма
     * @return список пользователей
     */
    List<Integer> findAllIdUsersLikesFilm(int idFilm);


    /**
     * Вернуть список из n самых популярных фильмов
     *
     * @param n количество фильмов в списке
     * @return список популярных фильмов
     */
    List<Film> getPop(int n);
}
