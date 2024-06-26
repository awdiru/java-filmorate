package ru.yandex.practicum.filmorate.controller.service.director_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.DirectorService;
import ru.yandex.practicum.filmorate.controller.storage.DirectorStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Director;

import java.util.List;

@Service
@Qualifier("DirectorServiceImpl")
public class DirectorServiceImpl implements DirectorService {
    private final DirectorStorage directorStorage;

    @Autowired
    public DirectorServiceImpl(@Qualifier("DirectorStorageDao") DirectorStorage directorStorage) {
        this.directorStorage = directorStorage;
    }

    @Override
    public Director createDirector(Director director) {
        return directorStorage.createDirector(director);
    }

    @Override
    public Director updateDirector(Director director) throws IncorrectIdException {
        getById(director.getId());
        return directorStorage.updateDirector(director);
    }

    @Override
    public Director getById(Integer id) throws IncorrectIdException {
        Director director = directorStorage.getById(id);
        if (director == null)
            throw new IncorrectIdException("Режиссер с id " + id + " не найден.");
        return director;
    }

    @Override
    public List<Director> findAllDirectors() {
        return directorStorage.findAllDirectors();
    }

    @Override
    public void deleteDirector(Integer id) throws IncorrectIdException {
        getById(id);
        directorStorage.deleteDirector(id);
    }
}
