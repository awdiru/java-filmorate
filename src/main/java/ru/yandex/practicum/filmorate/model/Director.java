package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
public class Director {
    private Integer id;
    @NotBlank(message = "Ошибка добавления режиссера! Имя режиссера не может быть пустым.")
    private String name;
}
