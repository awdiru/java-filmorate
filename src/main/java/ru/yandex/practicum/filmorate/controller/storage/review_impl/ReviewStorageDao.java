package ru.yandex.practicum.filmorate.controller.storage.review_impl;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviewDislikes;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviewLikes;
import ru.yandex.practicum.filmorate.controller.dao.DaoReviews;
import ru.yandex.practicum.filmorate.controller.storage.ReviewStorage;
import ru.yandex.practicum.filmorate.model.Review;

import java.util.List;

@Component
@AllArgsConstructor
@Qualifier("ReviewStorageDao")
public class ReviewStorageDao implements ReviewStorage {

    @Qualifier("DaoReviewsImpl")
    DaoReviews daoReviews;
    @Qualifier("DaoReviewLikesImpl")
    DaoReviewLikes daoReviewLikes;
    @Qualifier("DaoReviewDislikesImpl")
    DaoReviewDislikes daoReviewDislikes;

    @Override
    public Review addReview(Review review) {
        return daoReviews.addReview(review);
    }

    @Override
    public Review getReview(int id) {
        Review review = daoReviews.getReview(id);
        return review;
    }

    @Override
    public List<Review> getFilmReviews(int filmId, int count) {
        List<Review> filmReviews = daoReviews.getFilmReviews(filmId, count);
        return filmReviews;
    }

    @Override
    public List<Review> getAllReviews() {
        List<Review> allReviews = daoReviews.getAllReviews();
        return allReviews;
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
        daoReviewLikes.addLike(reviewId, userId);
    }

    @Override
    public void deleteLike(int reviewId, int userId) {
        daoReviewLikes.deleteLike(reviewId, userId);
    }

    @Override
    public void addDislike(int reviewId, int userId) {
        daoReviewDislikes.addDislike(reviewId, userId);
    }

    @Override
    public void deleteDislike(int reviewId, int userId) {
        daoReviewDislikes.deleteDislike(reviewId, userId);
    }

}
