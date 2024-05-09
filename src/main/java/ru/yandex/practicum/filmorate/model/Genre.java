package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Genre {
    @NotNull
    private Integer id;
    @NotNull
    @EqualsAndHashCode.Exclude
    private String name;
}