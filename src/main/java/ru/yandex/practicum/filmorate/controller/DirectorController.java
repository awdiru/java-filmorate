package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.DirectorService;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Director;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/directors")
public class DirectorController {
    private final DirectorService directorService;

    @Autowired
    public DirectorController(@Qualifier("DirectorServiceImpl") DirectorService directorService) {
        this.directorService = directorService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Director addDirectorFilm(@Valid @RequestBody Director director) {
        log.info("Создание режиссера: {}", director);
        return directorService.addDirectorFilm(director);
    }

    @PutMapping
    public Director updateDirector(@Valid @RequestBody Director director) {
        log.info("Обновление режиссера: {}", director);
        try {
            return directorService.updateDirector(director);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Попытка обновления несуществующего режиссера." + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Director getById(@PathVariable Integer id) {
        log.info("Получение режиссера с id: {}", id);
        try {
            return directorService.getById(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка поиска режиссера! " + e.getMessage());
        }
    }

    @GetMapping
    public List<Director> findAll() {
        log.info("Получение всех режиссеров");
        return directorService.findAllDirectors();
    }

    @DeleteMapping("/{id}")
    public void deleteDirector(@PathVariable Integer id) throws IncorrectIdException {
        log.info("Удаление режиссера по id: {}", id);
        directorService.deleteDirector(id);
    }
}
