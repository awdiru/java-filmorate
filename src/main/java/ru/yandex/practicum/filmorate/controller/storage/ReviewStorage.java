package ru.yandex.practicum.filmorate.controller.storage;

import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

public interface ReviewStorage {

    Review addReview(Review review);

    Review getReview(int id);

    List<Review> getFilmReviews(int filmId, int count);

    List<Review> getAllReviews();

    Review updateReview(Review review);

    void deleteReview(int id);

    void addLike(int reviewId, int userId);

    void deleteLike(int reviewId, int userId);

    void addDislike(int reviewId, int userId);

    void deleteDislike(int reviewId, int userId);
}
