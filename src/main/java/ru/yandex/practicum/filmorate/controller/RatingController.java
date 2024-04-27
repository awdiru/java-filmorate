package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.RatingService;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(@Qualifier("RatingServiceImpl") RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{id}")
    public Rating getById(@PathVariable Integer id) {
        try {
            return ratingService.getById(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка поиска рейтинга! " + e.getMessage());
        }
    }

    @GetMapping
    public List<Rating> findAll() {
        return ratingService.findAllRatings();
    }
}
