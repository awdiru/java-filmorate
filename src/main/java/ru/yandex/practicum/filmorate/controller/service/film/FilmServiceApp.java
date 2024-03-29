package ru.yandex.practicum.filmorate.controller.service.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Service
public class FilmServiceApp implements FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    @Autowired
    public FilmServiceApp(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    @Override
    public Film addLike(int idFilm, int idUser) throws IncorrectIdException {
        Film film = filmStorage.search(idFilm);
        User user = userService.search(idUser);
        if (film == null)
            throw new IncorrectIdException("Фильм с ID " + idFilm + " не найден.");
        else if (user == null)
            throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден.");

        Set<String> likes = film.getLikes();
        if (likes == null)
            likes = new HashSet<>();
        likes.add(user.getEmail());
        film.setLikes(likes);
        return film;
    }

    @Override
    public Film delLike(int idFilm, int idUser) throws IncorrectIdException {
        Film film = filmStorage.search(idFilm);
        User user = userService.search(idUser);
        if (film == null)
            throw new IncorrectIdException("Фильм с ID " + idFilm + " не найден.");
        else if (user == null)
            throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден.");

        Set<String> likes = film.getLikes();
        if (likes != null) {
            likes.remove(user.getEmail());
            film.setLikes(likes);
        }
        return film;
    }

    @Override
    public List<Film> popFilms(int count) {
        List<Film> films = new ArrayList<>(filmStorage.findAll());
        sortFilm(films);

        if (count >= films.size())
            count = films.size();
        if (count < 0)
            count = 0;

        List<Film> getFilms = new ArrayList<>(count);
        for (int i = films.size() - 1; i >= films.size() - count; i--) {
            getFilms.add(films.get(i));
        }
        return getFilms;
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public Film update(Film film) {
        return filmStorage.update(film);
    }

    @Override
    public Film delete(Film film) {
        return filmStorage.delete(film);
    }

    @Override
    public Film search(int id) {
        return filmStorage.search(id);
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    private void sortFilm(List<Film> films) {
        if (films.size() == 1 || films.isEmpty())
            return;

        boolean[] boolArray = new boolean[films.size()];
        Arrays.fill(boolArray, false);

        for (int i = 0; i < films.size(); i++) {
            if (boolArray[i])
                continue;
            if (films.get(i).getLikes() == null)
                films.get(i).setLikes(new HashSet<>());

            Film cash = films.get(i);
            int index;
            int oldIndex = i;

            do {
                index = i;
                for (int j = i; j < films.size(); j++) {
                    if (films.get(j) == null)
                        films.get(j).setLikes(new HashSet<>());
                    if (films.get(j).getLikes().size() < cash.getLikes().size())
                        index++;
                }
                while (boolArray[index])
                    index++;

                boolArray[index] = true;

                if (oldIndex == index)
                    break;

                Film swap = cash;
                cash = films.get(index);
                films.set(index, swap);

                oldIndex = index;
            } while (i != index);
        }
    }
}