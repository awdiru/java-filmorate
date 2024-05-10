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
     * @param findByDirector указатель для поиска по режиссеру
     * @param findByTitle указатель для поиска по назканию
     * @return список найденных фильмов или пустой список
     */
    List<Film> searchByParam(String query, boolean findByDirector, boolean findByTitle);

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