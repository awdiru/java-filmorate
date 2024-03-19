package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.InternalServerErrorRequestException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();
    private int generateId = 1;

    @PostMapping
    public Film add(@RequestBody @Valid Film film) throws BadRequestException {
        if (films.containsKey(film.getId())) {
            log.info("Ошибка добавления фильма! Такой фильм уже добавлен.");
            throw new BadRequestException();
        }
        film.setId(generateId++);
        films.put(film.getId(), film);
        log.info("Добавлен новый фильм. ID = " + film.getId());
        return film;
    }

    @PutMapping
    public Film update(@RequestBody @Valid Film film) throws InternalServerErrorRequestException {
        if (!films.containsKey(film.getId())) {
            log.info("Ошибка обновления фильма! Запрашиваемый id = " + film.getId() + " отсутствует.");
            throw new InternalServerErrorRequestException();
        }

        films.put(film.getId(), film);
        log.info("Фильм обновлен. ID = " + film.getId());
        return film;
    }

    @GetMapping
    public ArrayList<Film> findAll() {
        return new ArrayList<>(films.values());
    }
}