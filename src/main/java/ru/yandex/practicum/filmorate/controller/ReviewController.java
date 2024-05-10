package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.ReviewService;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Review;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    @Qualifier("ReviewServiceImpl")
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public Review getReview(@PathVariable int id) {
        try {
            return reviewService.getReview(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка получения отзыва! " + e.getMessage());
        }
    }

    @GetMapping
    public List<Review> getReviews(@RequestParam Optional<Integer> filmId, @RequestParam(defaultValue = "10") int count) {
        try {
            if (filmId.isPresent()) {
                return reviewService.getFilmReviews(filmId.get(), count);
            }
            return reviewService.getAllReviews();
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка получения отзывов! " + e.getMessage());
        }
    }

    @PostMapping
    public Review addReview(@Valid @RequestBody Review review) {
        try {
            return reviewService.addReview(review);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления отзыва! " + e.getMessage());
        }
    }

    @PutMapping
    public Review updateReview(@Valid @RequestBody Review review) {
        try {
            return reviewService.updateReview(review);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка обновления отзыва! " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable int id) {
        try {
            reviewService.deleteReview(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления отзыва! " + e.getMessage());
        }
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(@PathVariable int id, @PathVariable int userId) {
        try {
            reviewService.addLike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления like! " + e.getMessage());
        }
    }

    @PutMapping("/{id}/dislike/{userId}")
    public void addDislike(@PathVariable int id, @PathVariable int userId) {
        try {
            reviewService.addDislike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления dislike! " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) {
        try {
            reviewService.deleteLike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления like! " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}/dislike/{userId}")
    public void deleteDislike(@PathVariable int id, @PathVariable int userId) {
        try {
            reviewService.deleteDislike(id, userId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления dislike! " + e.getMessage());
        }
    }


}
