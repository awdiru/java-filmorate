package ru.yandex.practicum.filmorate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")

public class InternalServerErrorRequestException extends Exception {
    public InternalServerErrorRequestException() {
    }
}
