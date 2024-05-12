package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("UserServiceImpl") UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@RequestBody @Valid User user) {
        return userService.add(user);
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        try {
            return userService.update(user);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка обновления пользователя! " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable Integer id) {
        try {
            return userService.delete(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления фильма! " + e.getMessage());
        }
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User search(@PathVariable int id) {
        try {
            return userService.search(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка поиска пользователя! " + e.getMessage());
        }
    }

    @GetMapping("/{id}/feed")
    public List<Event> getFeed(@PathVariable int id) {
        try {
            return userService.getFeed(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка получения ленты событий! " + e.getMessage());
        }
    }

    @PutMapping("/{id}/friends/{friendId}")
    public User addFriend(@PathVariable int id, @PathVariable int friendId) {
        User user;
        try {
            user = userService.addFriend(id, friendId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка добавления друга! " + e.getMessage());
        }
        return user;
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public User deleteFriend(@PathVariable int id, @PathVariable int friendId) {
        User user;
        try {
            user = userService.deleteFriend(id, friendId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка удаления друга! " + e.getMessage());
        }
        return user;
    }

    @GetMapping("/{id}/friends")
    public List<User> getFriends(@PathVariable int id) {
        List<User> getFriends;
        try {
            getFriends = userService.getFriends(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка возвращения списка друзей! " + e.getMessage());
        }
        return getFriends;
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public List<User> getMutualFriends(@PathVariable int id, @PathVariable int otherId) {
        try {
            return userService.getMutualFriends(id, otherId);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка возвращения списка общих друзей! " + e.getMessage());
        }
    }

    @GetMapping("/{id}/recommendations")
    public List<Film> getRecommendations(@PathVariable int id) {
        try {
            return userService.getRecommendations(id);
        } catch (IncorrectIdException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка возвращения списка рекомендаций! " + e.getMessage());
        }
    }
}
