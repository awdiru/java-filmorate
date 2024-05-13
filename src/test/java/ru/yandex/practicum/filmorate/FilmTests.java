package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilmTests {
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void filmWithoutErrors() {
        Film film = new Film(1, "filmName", "filmDescription",
                LocalDate.of(1990, 01, 01), 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertTrue(violations.isEmpty());
    }

    @Test
    void filmNameNull() {
        Film film = new Film(1, null, "filmDescription",
                LocalDate.of(1990, 01, 01), 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void filmDescriptionSizeIncorrect() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            s.append("a");
        }
        String description = s.toString();
        Film film = new Film(1, "filmName", description,
                LocalDate.of(1990, 01, 01), 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void filmReleaseDateIncorrect() {
        Film film = new Film(1, "filmName", "filmDescription",
                LocalDate.of(1190, 01, 01), 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void filmReleaseDateNull() {
        Film film = new Film(1, "filmName", "filmDescription",
                null, 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void filmDurationNull() {
        Film film = new Film(1, "filmName", "filmDescription",
                LocalDate.of(1990, 01, 01), null,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void filmAllFieldIncorrect() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 300; i++) {
            s.append("a");
        }
        String description = s.toString();
        Film film = new Film(0, null, description,
                LocalDate.of(1190, 01, 01), -1,
                null, null, null, null);
        Set<ConstraintViolation<Film>> violations = validator.validate(film);
        for (ConstraintViolation<Film> f : violations) {
            System.out.println("\n" + f + "\n");
        }
        assertEquals(violations.size(), 4);
    }
}
