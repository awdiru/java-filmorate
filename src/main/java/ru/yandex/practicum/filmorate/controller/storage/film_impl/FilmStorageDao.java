package ru.yandex.practicum.filmorate.controller.storage.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoDirectors;
import ru.yandex.practicum.filmorate.controller.dao.DaoFilms;
import ru.yandex.practicum.filmorate.controller.dao.DaoGenres;
import ru.yandex.practicum.filmorate.controller.dao.DaoLikes;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoDirectorsImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoFilmsImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoGenresImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoLikesImpl;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.LinkedList;
import java.util.List;

@Component
@Qualifier("FilmStorageDao")
public class FilmStorageDao implements FilmStorage {
    private final DaoFilms daoFilms;
    private final DaoLikes daoLikes;

    private final DaoGenres daoGenres;
    private final DaoDirectors daoDirectors;

    @Autowired
    public FilmStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoFilms = new DaoFilmsImpl(jdbcTemplate);
        this.daoLikes = new DaoLikesImpl(jdbcTemplate);
        this.daoGenres = new DaoGenresImpl(jdbcTemplate);
        this.daoDirectors = new DaoDirectorsImpl(jdbcTemplate);
    }

    @Override
    public Film addLike(int idFilm, int idUser) {
        if (daoLikes.findAllIdUsersLikesFilm(idFilm).stream()
                .anyMatch(integer -> integer.equals(idUser))) {
            return search(idFilm);
        }
        daoLikes.addLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public List<Film> commonFilmsWithFriend(Integer userId, Integer friendId) {
        return daoFilms.commonFilmsWithFriend(userId, friendId);
    }

    @Override
    public Film deleteLike(int idFilm, int idUser) {
        daoLikes.delLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public Film add(Film film) {
        if (film == null) return null;
        if (film.getId() == null) film.setId(1);
        daoFilms.add(film);

        if (film.getLikes() == null) film.setLikes(new LinkedList<>());
        if (film.getGenres() == null) film.setGenres(new LinkedList<>());
        if (film.getDirectors() == null) film.setDirectors(new LinkedList<>());

        if (!film.getLikes().isEmpty()) {
            for (Integer id : film.getLikes())
                daoLikes.addLike(film.getId(), id);
        }
        if (!film.getGenres().isEmpty()) {
            for (Genre g : film.getGenres())
                daoGenres.addGenreFilm(film.getId(), g.getId());
        }
        if (!film.getDirectors().isEmpty()) {
            daoDirectors.addFilmDirectors(film.getId(), film.getDirectors());
        }
        return film;
    }

    @Override
    public Film update(Film film) {
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
    public List<Film> searchByParam(String query, List<String> by) {
        return daoFilms.searchByParam(query, by);
    }

    @Override
    public List<Film> findAll() {
        return daoFilms.findAll();
    }

    @Override
    public List<Film> getNPopularFilms(Integer n, Integer genreId, Integer year) {
        return daoLikes.getNPopularFilms(n, genreId, year);
    }

    @Override
    public List<Film> getFilmsByDirector(Integer directorId, String sortBy) {
        return daoFilms.getFilmsByDirector(directorId, sortBy);
    }
}
