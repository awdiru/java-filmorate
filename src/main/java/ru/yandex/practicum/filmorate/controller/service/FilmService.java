package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectYearException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmService {
    /**
     * Добавить лайк фильму
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     * @return обновленный фильм
     * @throws IncorrectIdException некорректный идентификатор
     */
    Film addLike(int idFilm, int idUser) throws IncorrectIdException;

    /**
     * Удалить лайк у фильма
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     * @return обновленный фильм
     * @throws IncorrectIdException некорректный идентификатор
     */
    Film delLike(int idFilm, int idUser) throws IncorrectIdException;

    /**
     * Добавить фильм
     *
     * @param film добавляемый фильм
     * @return добавленный фильм
     */
    Film add(Film film);

    /**
     * Обновить фильм
     *
     * @param film обновляемый фильм
     * @return обновленный фильм
     */
    Film update(Film film) throws IncorrectIdException;

    /**
     * Удалить фильм
     *
     * @param id идентификатор удаляемого фильма
     * @return удаленный фильм
     */
    Film delete(Integer id) throws IncorrectIdException;

    /**
     * Найти фильм
     *
     * @param id идентификатор фильма
     * @return найденный фильм или null
     */
    Film search(int id) throws IncorrectIdException;

    /**
     * Вернуть список всех фильмов
     *
     * @return список всех фильмов
     */
    List<Film> findAll();

    /**
     * Вернуть список самых популярных фильмов одного жанра за один год
     *
     * @param n       количество фильмов
     * @param genreId идентификатор жанра
     * @param year    год
     * @return список фильмов
     */
    List<Film> getNPopularFilms(Integer n, Integer genreId, Integer year) throws IncorrectIdException, IncorrectYearException;
}
