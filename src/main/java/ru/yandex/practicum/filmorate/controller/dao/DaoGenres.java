package ru.yandex.practicum.filmorate.controller.dao;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface DaoGenres {
    /**
     * Добавить жанр фильму
     *
     * @param idFilm  идентификатор фильма
     * @param idGenre идентификатор жанра
     */
    void addGenreFilm(Integer idFilm, Integer idGenre);

    /**
     * Получить название жанра по идентификатору
     *
     * @param id идентификатор жанра
     * @return жанр
     */
    Genre getById(Integer id);

    /**
     * Получить список всех жанров
     *
     * @return список всех жанров
     */
    List<Genre> findAllGenres();

    /**
     * Получить список жанров фильма
     *
     * @param idFilm идентификатор фильма
     * @return список всех жанров фильма
     */
    List<Genre> getGenresFilm(Integer idFilm);

    /**
     * Обновляет все жанры фильма
     *
     * @param film фильм
     * @return обновленный фильм
     */
    Film updateGenresFilm(Film film);
}
