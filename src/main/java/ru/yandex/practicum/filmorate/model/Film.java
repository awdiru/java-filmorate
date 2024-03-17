package ru.yandex.practicum.filmorate.model;

import lombok.*;
//import ru.yandex.practicum.filmorate.annotation.films.maxCharacter.MaxCharacter;
import ru.yandex.practicum.filmorate.annotation.films.realeseDate.AcceptableReleaseDate;

//import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * Film.
 */
@Data
public class Film {
    private Integer id;
    //
    @NotBlank(message = "Название фильма не должно быть пустым")
    private String name;
    //
    @Size(max = 200, message = "Ошибка добавления фильма! Описание не может превышать 200 символов.")
    private String description;
    //
    @NotNull(message = "Ошибка добавления фильма! Дата релиза не может быть пустой.")
    @AcceptableReleaseDate(message = "Ошибка добавления фильма! Дата релиза не может быть раньше 28 декабря 1895 года.")
    private LocalDate releaseDate;
    //
    @NotNull(message = "Ошибка добавления фильма! Продолжительность не может быть пустой.")
    private Integer duration;
}
