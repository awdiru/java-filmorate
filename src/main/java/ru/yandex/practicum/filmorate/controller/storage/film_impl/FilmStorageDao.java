package ru.yandex.practicum.filmorate.controller.storage.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFilms;
import ru.yandex.practicum.filmorate.controller.dao.DaoLikes;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashSet;
import java.util.List;

@Component
@Qualifier("FilmStorageDao")
public class FilmStorageDao implements FilmStorage {
    private final DaoFilms daoFilms;
    private final DaoLikes daoLikes;

    @Autowired
    public FilmStorageDao(@Qualifier("DaoFilmsImpl") DaoFilms daoFilms,
                          @Qualifier("DaoLikesImpl") DaoLikes daoLikes) {
        this.daoFilms = daoFilms;
        this.daoLikes = daoLikes;
    }

    @Override
    public Film addLike(int idFilm, int idUser) {
        daoLikes.addLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public Film delLike(int idFilm, int idUser) {
        daoLikes.delLike(idFilm, idUser);
        return search(idFilm);
    }

    @Override
    public List<Film> popFilms(int n) {
        return daoLikes.getPop(n);
    }

    @Override
    public Film add(Film film) {
        if (film == null) return null;
        daoFilms.add(film);

        if (film.getLikes() == null) film.setLikes(new HashSet<>());
        if (film.getLikes().isEmpty()) return film;

        for (Integer id : film.getLikes())
            daoLikes.addLike(film.getId(), id);
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
        for (Integer idUser : film.getLikes())
            daoLikes.delLike(film.getId(), idUser);

        daoFilms.delete(id);
        return film;
    }

    @Override
    public Film search(int id) {
        Film film = daoFilms.search(id);
        if (film == null) return null;
        film.setLikes(new HashSet<>(daoLikes.findAllIdUsersLikesFilm(id)));
        return film;
    }

    @Override
    public List<Film> findAll() {
        return daoFilms.findAll();
    }
}
