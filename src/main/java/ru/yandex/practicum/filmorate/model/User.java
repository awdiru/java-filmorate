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
    @NotEmpty(message = "Ошибка добавления пользователя! email не может быть пустым.")
    @Email(message = "Ошибка добавления пользователя! email должен быть записан по форме 'user@email.org.'")
    private String email;
    //
    @NotEmpty(message = "Ошибка добавления пользователя! Логин не может быть пустым.")
    @WithoutSpaces(message = "Ошибка добавления пользователя! Логин не может содержать пробелов.")
    private String login;
    //
    private String name;
    //
    @NotNull(message = "Ошибка добавления пользователя! Дата рождения не может быть пустой.")
    @Past(message = "Ошибка добавления пользователя! Дата рождения не может быть в будущем.")
    private LocalDate birthday;
}
