package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public FilmController(@Qualifier("FilmServiceImpl") FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping
    public Film add(@RequestBody @Valid Film film) {
        try {
            return filmService.add(film);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ошибка добавления фильма! Переданы некорректные данные.");
        }
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) {
        try {
            Film result = filmService.update(film);
            return result;
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка Обновления фильма! " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Film search(@PathVariable Integer id) {
        try {
            return filmService.search(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка поиска фильма! " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public Film delete(@PathVariable Integer id) {
        try {
            return filmService.delete(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления фильма! " + e.getMessage());
        }
    }

    @GetMapping
    public List<Film> findAll() {
        return filmService.findAll();
    }

    @PutMapping("/{id}/like/{userId}")
    public Film addLike(@PathVariable int id, @PathVariable int userId) {
        try {
            return filmService.addLike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления лайка! " + e.getMessage());
        }
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
        return filmService.getNPopularFilms(count);
    }

    @GetMapping("/director/{directorId}")
    public List<Film> getFilmsByDirector(@PathVariable("directorId") Integer directorId,
                                         @RequestParam String sortBy) throws IncorrectIdException {
        try {
            return filmService.getFilmsByDirector(directorId, sortBy);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Режиссер с указанным id отсутствует в базе! " + e.getMessage());
        }
    }
}