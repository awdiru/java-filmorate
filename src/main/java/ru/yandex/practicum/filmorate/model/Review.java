package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class Review {
    int reviewId;
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Ошибка добавления отзыва! Содержание отзыва не может быть пустым.")
    @Size(max = 300, message = "Ошибка добавления отзыва! Содержание отзыва не может превышать 300 символов.")
    String content;
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Ошибка добавления отзыва! Отзыв не может быть нейтральным.")
    Boolean isPositive;
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Ошибка добавления отзыва! id пользователя не может быть пустым.")
    Integer userId;
    @EqualsAndHashCode.Exclude
    @NotNull(message = "Ошибка добавления отзыва! id фильма не может быть пустым.")
    Integer filmId;
    @EqualsAndHashCode.Exclude
    int useful;
}
