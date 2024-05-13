package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class Director {
    private Integer id;
    @NotBlank(message = "Ошибка добавления режиссера! Имя режиссера не может быть пустым.")
    @Size(max = 200, message = "Максимальная длина имени режиссера 200 символов")
    private String name;
}
