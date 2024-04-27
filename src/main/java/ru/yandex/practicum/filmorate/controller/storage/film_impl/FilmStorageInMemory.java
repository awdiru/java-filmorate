package ru.yandex.practicum.filmorate.controller.storage.film_impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Qualifier("FilmStorageInMemory")
public class FilmStorageInMemory implements FilmStorage {
    private final Map<Integer, Film> films;
    private int generateID;

    public FilmStorageInMemory() {
        films = new HashMap<>();
        generateID = 1;
    }

    @Override
    public Film addLike(int idFilm, int idUser) {
        Film film = films.get(idFilm);
        if (film == null) return null;
        film.getLikes().add(idUser);
        return film;
    }

    @Override
    public Film delLike(int idFilm, int idUser) {
        Film film = films.get(idFilm);
        if (film == null) return null;
        film.getLikes().remove(idUser);
        return film;
    }

    @Override
    public List<Film> popFilms(int n) {
        return films.values().stream()
                .filter(film -> !film.getLikes().isEmpty())
                .sorted((Film film1,Film film2) -> {
                    if (film1.getLikes().size() >= film1.getLikes().size())
                        return -1;
                    return 1;
                })
                .limit(n)
                .collect(Collectors.toList());
    }

    @Override
    public Film add(Film film) {
        film.setId(generateID);
        if (film.getLikes() == null)
            film.setLikes(new LinkedList<>());
        films.put(generateID++, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId()))
            return null;
        if (film.getLikes() == null)
            film.setLikes(new LinkedList<>());

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Integer id) throws IncorrectIdException {
        return films.remove(id);
    }

    @Override
    public Film search(int id) {
        return films.get(id);
    }

    @Override
    public List<Film> findAll() {
        return List.copyOf(films.values());
    }
}
