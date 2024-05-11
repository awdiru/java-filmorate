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
     * @return обновленный фильм
     */
    Film addLike(int idFilm, int idUser);

    /**
     * Удалить лайк у фильма
     *
     * @param idFilm идентификатор фильма
     * @param idUser идентификатор пользователя
     * @return обновленный фильм
     */
    Film deleteLike(int idFilm, int idUser);

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
}
