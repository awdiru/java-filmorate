package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
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
     * Список N самых популярных фильмов
     *
     * @param n количество фильмов
     * @return список фильмов
     */
    List<Film> getNPopularFilms(int n);

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
     * Поиск фильма по параметрам
     *
     * @param query текст для поиска
     * @param by значения director (поиск по режиссёру), title (поиск по названию)
     * @return список найденных фильмов или пустой список
     */
     List<Film> searchByParam(String query, String by);

    /**
     * Вернуть список всех фильмов
     *
     * @return список всех фильмов
     */
    List<Film> findAll();

    /**
     * Вернуть список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     *
     * @return список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     */
    List<Film> getFilmsByDirector(Integer directorId, String sortBy) throws IncorrectIdException;
}
