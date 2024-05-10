package ru.yandex.practicum.filmorate.controller.storage.review_impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviews;
import ru.yandex.practicum.filmorate.controller.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Component
@AllArgsConstructor
@Qualifier("ReviewStorageDao")
public class ReviewStorageDao implements ReviewStorage {

    @Qualifier("DaoReviewsImpl")
    private final DaoReviews daoReviews;

    @Override
    public Review addReview(Review review) {
        return daoReviews.addReview(review);
    }

    @Override
    public Review getReview(int id) {
        return daoReviews.getReview(id);
    }

    @Override
    public List<Review> getFilmReviews(int filmId, int count) {
        return daoReviews.getFilmReviews(filmId, count);
    }

    @Override
    public List<Review> getAllReviews() {
        return daoReviews.getAllReviews();
    }

    @Override
    public Review updateReview(Review review) {
        return daoReviews.updateReview(review);
    }

    @Override
    public void deleteReview(int id) {
        daoReviews.deleteReview(id);
    }

    @Override
    public void addLike(int reviewId, int userId) {
        daoReviews.addLike(reviewId, userId);
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        daoReviews.deleteLike(reviewId, userId);
    }

    @Override
    public void addDislike(int reviewId, int userId) {
        daoReviews.addDislike(reviewId, userId);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        daoReviews.deleteDislike(reviewId, userId);
    }

}
