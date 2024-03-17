package ru.yandex.practicum.filmorate.model;

import lombok.*;
import ru.yandex.practicum.filmorate.annotation.users.withoutSpaces.WithoutSpaces;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * User
 */
@Data
@AllArgsConstructor
public class User {
    @EqualsAndHashCode.Exclude
    private Integer id;
    //
    @NotBlank(message = "Ошибка добавления пользователя! email не может быть пустым.")
    @Email(message = "Ошибка добавления пользователя! email должен быть записан по форме 'user@email.org.'")
    private final String email;
    //
    @NotBlank(message = "Ошибка добавления пользователя! Логин не может быть пустым.")
    @WithoutSpaces(message = "Ошибка добавления пользователя! Логин не может содержать пробелов.")
    @EqualsAndHashCode.Exclude
    private final String login;
    //
    @EqualsAndHashCode.Exclude
    private String name;
    //
    @NotNull(message = "Ошибка добавления пользователя! Дата рождения не может быть пустой.")
    @Past(message = "Ошибка добавления пользователя! Дата рождения не может быть в будущем.")
    @EqualsAndHashCode.Exclude
    private LocalDate birthday;
}
