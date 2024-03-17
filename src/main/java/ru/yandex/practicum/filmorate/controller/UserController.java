package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.BadRequestException;
import ru.yandex.practicum.filmorate.exceptions.InternalServerErrorRequestException;
import ru.yandex.practicum.filmorate.model.User;


import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();
    private int generateId = 1;

    @PostMapping
    public User add(@RequestBody @Valid User user) throws BadRequestException {
        if (users.containsValue(user)) {
            log.info("Ошибка добавления пользователя! Пользователь с указанным email уже существует");
            throw new BadRequestException();
        }
        if (user.getName() == null)
            user.setName(user.getLogin());

        user.setId(generateId++);
        users.put(user.getId(), user);
        log.info("Добавлен новый пользователь. ID = " + user.getId());
        return user;
    }

    @PutMapping
    public User update(@RequestBody @Valid User user) throws InternalServerErrorRequestException {
        if (user.getId() == null) {
            log.info("Ошибка обновления пользователя! Передан ID = null.");
            throw new InternalServerErrorRequestException();
        } else if (!users.containsKey(user.getId())) {
            log.info("Ошибка обновления пользователя! Запрашиваемый пользователь отсутствует.");
            throw new InternalServerErrorRequestException();
        }
        users.put(user.getId(), user);
        log.info("Пользователь обновлен. ID = " + user.getId());
        return user;
    }

    @GetMapping
    public ArrayList<User> findAll() {
        return new ArrayList<>(users.values());
    }
}
