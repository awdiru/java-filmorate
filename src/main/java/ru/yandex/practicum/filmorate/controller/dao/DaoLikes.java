package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * Вернуть список идентификаторов пользователей с их лайками
     *
     * @return список лайков
     */
    Map<Integer, Set<Integer>> getAllUserIdWithLikes();

    /**
     * Вернуть список самых популярных фильмов одного жанра за один год
     *
     * @param n       количество фильмов
     * @param genreId идентификатор жанра
     * @param year    год
     * @return список фильмов
     */
    List<Film> getNPopularFilms(Integer n, Integer genreId, Integer year);
}
