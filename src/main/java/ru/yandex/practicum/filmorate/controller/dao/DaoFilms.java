package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DaoFilms {
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
    List<Film> commonFilmsWithFriend(Integer userId, Integer friendId);

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
    Film search(Integer id);

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
     * Вернуть список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     *
     * @return список фильмов режиссера, отсортированных по количеству лайков или году выпуска
     */
    List<Film> getFilmsByDirector(Integer directorId, String sortBy);
}