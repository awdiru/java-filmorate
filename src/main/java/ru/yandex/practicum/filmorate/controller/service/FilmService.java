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
     * @param rating оценка пользователя
     * @return обновленный фильм
     * @throws IncorrectIdException некорректный идентификатор
     */
    Film addLike(int idFilm, int idUser, int rating) throws IncorrectIdException;

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
     * Получить общие с другом фильмы.
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     * @return список фильмов
     */
    List<Film> commonFilmsWithFriend(Integer userId, Integer friendId) throws IncorrectIdException;

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
     * @param by лист значений: director (поиск по режиссёру), title (поиск по названию)
     * @return список найденных фильмов или пустой список
     */
     List<Film> searchByParam(String query, List<String> by);

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

    /**
     * Вернуть список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     *
     * @return список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     */
    List<Film> getFilmsByDirector(Integer directorId, String sortBy) throws IncorrectIdException;
}
