package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DaoFilms {
    Film add(Film film);

    /**
     * Обновить фильм
     * @param film обновляемый фильм
     * @return обновленный фильм
     */
    Film update(Film film);

    /**
     * Удалить фильм
     * @param id идентификатор удаляемого фильма
     * @return удаленный фильм
     */
    Film delete(Integer id);

    /**
     * Найти фильм
     * @param id идентификатор фильма
     * @return найденный фильм или null
     */
    Film search(Integer id);

    /**
     * Вернуть список всех фильмов
     * @return список всех фильмов
     */
    List<Film> findAll();
}
