package ru.yandex.practicum.filmorate.controller.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Component("DbUserStorage")
public class DbUserStorage implements UserStorage {
    @Override
    public User create(User user) {
        return null;
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public User delete(User user) {
        return null;
    }

    @Override
    public User search(int id) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return null;
    }
}
