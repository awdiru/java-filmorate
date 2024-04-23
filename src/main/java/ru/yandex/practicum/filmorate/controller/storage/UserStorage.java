package ru.yandex.practicum.filmorate.controller.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserStorage {
    User create(User user);

    User update(User user);

    User delete(User user);

    User search(int id);

    List<User> findAll();
}
