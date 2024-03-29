package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmStorage) {
        this.filmService = filmStorage;
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        Film addFilm = filmService.add(film);
        if (addFilm == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ошибка добавления фильма! Фильм с ID " + film.getId() + " уже существует.");
        return addFilm;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        Film updFilm = filmService.update(film);
        if (updFilm == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка обновления фильма! Фильм с ID " + film.getId() + " не найден.");
        return updFilm;
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        Film film;
        try {
            film = filmService.addLike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления лайка! " + e.getMessage());
        }
        return film;
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film delLike(@PathVariable int id, @PathVariable int userId) {
        Film film;
        try {
            film = filmService.delLike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления лайка! " + e.getMessage());
        }
        return film;
    }

    @GetMapping("/popular")
    public List<Film> popFilms(@RequestParam(defaultValue = "10") int count) {
        return filmService.popFilms(count);
    }
}