package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectRatingException extends RuntimeException {
    public IncorrectRatingException(String message) {
        super(message);
    }
}
