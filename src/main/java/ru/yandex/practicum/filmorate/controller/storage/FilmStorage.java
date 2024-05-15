package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    /**
     * Добавить лайк фильму
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     * @param rating оценка пользователя
     * @return обновленный фильм
     */
    Film addLike(int idFilm, int idUser, int rating);

    /**
     * Получить общие с другом фильмы.
     *
     * @param userId   идентификатор пользователя
     * @param friendId идентификатор друга
     * @return список фильмов
     */
    List<Film> commonFilmsWithFriend(Integer userId, Integer friendId);

    /**
     * Удалить лайк у фильма
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     * @return обновленный фильм
     */
    Film deleteLike(int idFilm, int idUser);

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
    Film update(Film film);

    /**
     * Удалить фильм
     *
     * @param id идентификатор удаляемого фильма
     * @return удаленный фильм
     */
    Film delete(Integer id);

    /**
     * Найти фильм
     *
     * @param id идентификатор фильма
     * @return найденный фильм или null
     */
    Film search(int id);

    /**
     * Поиск фильма по параметрам
     *
     * @param query текст для поиска
     * @param by указатель для поиска по режиссеру
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
     * Вернуть список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     *
     * @return список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     */
    List<Film> getFilmsByDirector(Integer directorId, String sortBy) throws IncorrectIdException;

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