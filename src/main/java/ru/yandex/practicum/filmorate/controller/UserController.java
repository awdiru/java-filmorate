package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.practicum.filmorate.controller.service.user.UserServiceApp;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserServiceApp userService;

    @Autowired
    public UserController(UserServiceApp userService) {
        this.userService = userService;
    }

    @PostMapping
    public User add(@RequestBody @Valid User user) {
        User addUser = userService.create(user);
        if (addUser == null)
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Ошибка добавления пользователя! Пользователь с email " + user.getEmail() + " уже существует.");
        return addUser;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) {
        User updUser = userService.update(user);
        if (updUser == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Ошибка обновления пользователя! Пользователь с ID " + user.getId() + " не найден.");
        return updUser;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public User search(@PathVariable int id) {
        User user = userService.search(id);
        if (user == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ошибка поиска пользователя! Пользователь с ID " + id + " не найден.");
        return user;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Ошибка возвращения списка общих друзей! " + e.getMessage());
        }
    }
}
