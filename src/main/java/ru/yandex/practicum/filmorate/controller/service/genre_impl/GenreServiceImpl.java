package ru.yandex.practicum.filmorate.controller.service.genre_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.GenreService;
import ru.yandex.practicum.filmorate.controller.storage.GenreStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

@Service
@Qualifier("GenreServiceImpl")
public class GenreServiceImpl implements GenreService {
    private final GenreStorage genreStorage;

    @Autowired
    public GenreServiceImpl(@Qualifier("GenreStorageDao") GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    @Override
    public Genre getById(Integer id) throws IncorrectIdException {
        Genre genre = genreStorage.getById(id);
        if (genre == null)
            throw new IncorrectIdException("Жанр с id " + id + " не найден.");
        return genre;
    }

    @Override
    public List<Genre> findAllGenres() {
        return genreStorage.findAllGenres();
    }
}
