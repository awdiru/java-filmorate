package ru.yandex.practicum.filmorate.controller.service.review_impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.FilmService;
import ru.yandex.practicum.filmorate.controller.service.ReviewService;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Service
@AllArgsConstructor
@Qualifier("ReviewServiceImpl")
public class ReviewServiceImpl implements ReviewService {

    @Qualifier("ReviewStorageDao")
    private final ReviewStorage reviewStorage;
    @Qualifier("FilmServiceImpl")
    private final FilmService filmService;
    @Qualifier("UserServiceImpl")
    private final UserService userService;

    @Override
    public Review addReview(Review review) throws IncorrectIdException {
        filmService.search(review.getFilmId());
        userService.search(review.getUserId());
        return reviewStorage.addReview(review);
    }

    @Override
    public Review getReview(int id) throws IncorrectIdException {
        Review review = reviewStorage.getReview(id);
        if (review == null) {
            throw new IncorrectIdException(String.format("Отзыва с id = {} не существует", id));
        }
        return review;
    }

    @Override
    public List<Review> getFilmReviews(int filmId, int count) throws IncorrectIdException {
        filmService.search(filmId);
        return reviewStorage.getFilmReviews(filmId, count);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewStorage.getAllReviews();
    }

    @Override
    public Review updateReview(Review review) throws IncorrectIdException {
        getReview(review.getReviewId());
        return reviewStorage.updateReview(review);
    }

    @Override
    public void deleteReview(int id) throws IncorrectIdException {
        getReview(id);
        reviewStorage.deleteReview(id);
    }

    @Override
    public void addLike(int reviewId, int userId) throws IncorrectIdException {
        userService.search(userId);
        getReview(reviewId);
        reviewStorage.addLike(reviewId, userId);
        reviewStorage.deleteDislike(reviewId, userId);
    }

    @Override
    public void deleteLike(int reviewId, int userId) throws IncorrectIdException {
        userService.search(userId);
        getReview(reviewId);
        reviewStorage.deleteLike(reviewId, userId);
    }

    @Override
    public void addDislike(int reviewId, int userId) throws IncorrectIdException {
        userService.search(userId);
        getReview(reviewId);
        reviewStorage.addDislike(reviewId, userId);
        reviewStorage.deleteLike(reviewId, userId);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) throws IncorrectIdException {
        userService.search(userId);
        getReview(reviewId);
        reviewStorage.deleteDislike(reviewId, userId);
    }

}
