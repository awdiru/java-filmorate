package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewService {

    Review addReview(Review review) throws IncorrectIdException;

    Review getReview(int id) throws IncorrectIdException;

    List<Review> getFilmReviews(int filmId, int count) throws IncorrectIdException;

    List<Review> getAllReviews();

    Review updateReview(Review review) throws IncorrectIdException;

    void deleteReview(int id) throws IncorrectIdException;

    void addLike(int reviewId, int userId) throws IncorrectIdException;

    void deleteLike(int reviewId, int userId) throws IncorrectIdException;

    void addDislike(int reviewId, int userId) throws IncorrectIdException;

    void deleteDislike(int reviewId, int userId) throws IncorrectIdException;
}
