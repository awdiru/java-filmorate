package ru.yandex.practicum.filmorate.controller.service.rating_impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.RatingService;
import ru.yandex.practicum.filmorate.controller.storage.RatingStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;

@Service
@Qualifier("RatingServiceImpl")
public class RatingServiceImpl implements RatingService {
    private final RatingStorage ratingStorage;

    public RatingServiceImpl(@Qualifier("RatingStorageDao") RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    @Override
    public Rating getById(Integer id) throws IncorrectIdException {
        Rating rating = ratingStorage.getById(id);
        if (rating == null)
            throw new IncorrectIdException("Рейтинг с id " + id + " не найден.");
        return rating;
    }

    @Override
    public List<Rating> findAllRatings() {
        return ratingStorage.findAllRatings();
    }
}
