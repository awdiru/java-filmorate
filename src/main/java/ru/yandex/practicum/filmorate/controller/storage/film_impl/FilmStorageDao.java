package ru.yandex.practicum.filmorate.controller.storage.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFilms;
import ru.yandex.practicum.filmorate.controller.dao.DaoGenres;
import ru.yandex.practicum.filmorate.controller.dao.DaoLikes;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoFilmsImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoGenresImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoLikesImpl;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
@Qualifier("FilmStorageDao")
public class FilmStorageDao implements FilmStorage {
    private final DaoFilms daoFilms;
    private final DaoLikes daoLikes;

    private final DaoGenres daoGenres;

    @Autowired
    public FilmStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoFilms = new DaoFilmsImpl(jdbcTemplate);
        this.daoLikes = new DaoLikesImpl(jdbcTemplate);
        this.daoGenres = new DaoGenresImpl(jdbcTemplate);
    }

    @Override
    public Film addLike(int idFilm, int idUser) {
        daoLikes.addLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public Film deleteLike(int idFilm, int idUser) {
        daoLikes.delLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public List<Film> getNPopularFilms(int n) {
        return daoLikes.getPop(n);
    }

    @Override
    public Film add(Film film) {
        if (film == null) return null;
        if (film.getId() == null) film.setId(1);
        daoFilms.add(film);

        if (film.getLikes() == null) film.setLikes(new LinkedList<>());
        if (film.getGenres() == null) film.setGenres(new LinkedList<>());

        if (!film.getLikes().isEmpty()) {
            for (Integer id : film.getLikes())
                daoLikes.addLike(film.getId(), id);
        }
        if (!film.getGenres().isEmpty()) {
            for (Genre g : film.getGenres())
                daoGenres.addGenreFilm(film.getId(), g.getId());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        //film.setGenres(daoGenres.getGenresFilm(film.getId()));
        return daoFilms.update(film);
    }

    @Override
    public Film delete(Integer id) {
        Film film = search(id);
        if (film == null) return null;
        return daoFilms.delete(id);
    }

    @Override
    public Film search(int id) {
        Film film = daoFilms.search(id);
        if (film == null) return null;
        film.setLikes(daoLikes.findAllIdUsersLikesFilm(id));
        return film;
    }

    @Override
    public List<Film> findAll() {
        return daoFilms.findAll();
    }

    @Override
    public List<Film> getFilmsByDirector(Integer directorId, String sortBy) throws IncorrectIdException {
        return daoFilms.getFilmsByDirector(directorId, sortBy);
    }
}
