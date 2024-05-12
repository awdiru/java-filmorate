package ru.yandex.practicum.filmorate.controller.service.film_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.DirectorService;
import ru.yandex.practicum.filmorate.controller.service.FeedService;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.controller.storage.GenreStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.exceptions.IncorrectYearException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

@Service
@Qualifier("FilmServiceImpl")
public class FilmServiceImpl implements FilmService {
    private final FilmStorage filmStorage;
    private final GenreStorage genreStorage;
    private final UserService userService;
    private final DirectorService directorService;
    private final FeedService feedService;

    @Autowired
    public FilmServiceImpl(@Qualifier("FilmStorageDao") FilmStorage filmStorage,
                           @Qualifier("UserServiceImpl") UserService userService,
                           @Qualifier("GenreStorageDao") GenreStorage genreStorage,
                           @Qualifier("DirectorServiceImpl") DirectorService directorService,
                           @Qualifier("FeedServiceImpl") FeedService feedService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
        this.genreStorage = genreStorage;
        this.directorService = directorService;
        this.feedService = feedService;
    }

    @Override
    public Film addLike(int idFilm, int idUser) throws IncorrectIdException {
        search(idFilm);
        userService.search(idUser);
        feedService.addAddLikeEvent(idUser, idFilm);
        return filmStorage.addLike(idFilm, idUser);
    }

    @Override
    public Film delLike(int idFilm, int idUser) throws IncorrectIdException {
        search(idFilm);
        userService.search(idUser);
        feedService.addRemoveLikeEvent(idUser, idFilm);
        return filmStorage.deleteLike(idFilm, idUser);
    }

    @Override
    public Film add(Film film) {
        return filmStorage.add(film);
    }

    @Override
    public List<Film> commonFilmsWithFriend(Integer userId, Integer friendId) throws IncorrectIdException {
        userService.search(userId);
        userService.search(friendId);
        return filmStorage.commonFilmsWithFriend(userId, friendId);
    }

    @Override
    public Film update(Film film) throws IncorrectIdException {
        search(film.getId());
        return filmStorage.update(film);
    }

    @Override
    public Film delete(Integer id) throws IncorrectIdException {
        search(id);
        return filmStorage.delete(id);
    }

    @Override
    public Film search(int id) throws IncorrectIdException {
        Film film = filmStorage.search(id);
        if (film == null)
            throw new IncorrectIdException("Фильм с ID " + id + " не найден.");
        return film;
    }

    @Override
    public List<Film> searchByParam(String query, List<String> by) {
        query = protectedInjection(query);
        return filmStorage.searchByParam(query, by);
    }

    @Override
    public List<Film> findAll() {
        return filmStorage.findAll();
    }

    @Override
    public List<Film> getNPopularFilms(Integer n, Integer genreId, Integer year) throws
            IncorrectIdException, IncorrectYearException {
        if (genreId != null && genreStorage.getById(genreId) == null)
            throw new IncorrectIdException("Жанр с id " + genreId + " не найден.");
        if (year != null && year < 1895)
            throw new IncorrectYearException("Год не может быть раньше 1895.");
        return filmStorage.getNPopularFilms(n, genreId, year);
    }

    @Override
    public List<Film> getFilmsByDirector(Integer directorId, String sortBy) throws IncorrectIdException {
        directorService.getById(directorId);
        return filmStorage.getFilmsByDirector(directorId, sortBy);
    }

    private String protectedInjection(String str) {
        str = str.replace(";", "");
        str = str.replace("\"", "\\\"");
        return str;
    }
}