package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotation.films.realeseDate.AcceptableReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Set;

/**
 * Film.
 */
@Data
@AllArgsConstructor
public class Film {
    private int id;
    @NotBlank(message = "Название фильма не должно быть пустым")
    @EqualsAndHashCode.Exclude
    private String name;
    @Size(max = 200, message = "Ошибка добавления фильма! Описание не может превышать 200 символов.")
    @EqualsAndHashCode.Exclude
    private String description;
    @NotNull(message = "Ошибка добавления фильма! Дата релиза не может быть пустой.")
    @AcceptableReleaseDate(message = "Ошибка добавления фильма! Дата релиза не может быть раньше 28 декабря 1895 года.")
    @EqualsAndHashCode.Exclude
    private LocalDate releaseDate;
    @NotNull(message = "Ошибка добавления фильма! Продолжительность не может быть пустой.")
    @Positive
    @EqualsAndHashCode.Exclude
    private int duration;
    @EqualsAndHashCode.Exclude
    private Set<String> likes;
}
