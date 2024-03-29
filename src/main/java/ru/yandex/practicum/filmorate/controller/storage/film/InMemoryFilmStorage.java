package ru.yandex.practicum.filmorate.controller.storage.film;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films;
    private int generateID;

    public InMemoryFilmStorage() {
        films = new HashMap<>();
        generateID = 1;
    }

    @Override
    public Film add(Film film) {
        film.setId(generateID);
        if (film.getLikes() == null)
            film.setLikes(new HashSet<>());
        films.put(generateID++, film);
        return film;
    }

    @Override
    public Film update(Film film) {
        if (!films.containsKey(film.getId()))
            return null;
        if (film.getLikes() == null)
            film.setLikes(new HashSet<>());

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        return films.remove(film.getId());
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
