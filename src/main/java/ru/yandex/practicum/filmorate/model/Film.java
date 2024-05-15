package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotation.films.realeseDate.AcceptableReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private Integer id;
    @NotBlank(message = "Ошибка добавления фильма! Название фильма не может быть пустым.")
    @EqualsAndHashCode.Exclude
    private String name;
    @Size(max = 200, message = "Ошибка добавления фильма! Описание не может превышать 200 символов.")
    @EqualsAndHashCode.Exclude
    private String description;
    @NotNull(message = "Ошибка добавления фильма! Дата релиза не может быть пустой.")
    @AcceptableReleaseDate(message = "Ошибка добавления фильма! Дата релиза не может быть раньше 28 декабря 1895 года.")
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;
    @Positive
    @NotNull(message = "Ошибка добавления фильма! Продолжительность не может быть пустой.")
    @EqualsAndHashCode.Exclude
    private Integer duration;
    @EqualsAndHashCode.Exclude
    private Rating mpa;
    @EqualsAndHashCode.Exclude
    private List<Integer> likes;
    @EqualsAndHashCode.Exclude
    private List<Genre> genres;
    @EqualsAndHashCode.Exclude
    private List<Director> directors;
    @EqualsAndHashCode.Exclude
    private double rating;
}
