package ru.yandex.practicum.filmorate.controller.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users;
    private int genegateID;

    public InMemoryUserStorage() {
        users = new HashMap<>();
        genegateID = 1;
    }

    @Override
    public User create(User user) {
        for (User u : users.values())
            if (u.getEmail().equals(user.getEmail()))
                return null;

        if (user.getName() == null)
            user.setName(user.getLogin());

        if (user.getFriends() == null)
            user.setFriends(new HashSet<>());

        user.setId(genegateID++);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User update(User user) {
        if (users.remove(user.getId()) == null)
            return null;
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        return users.remove(user.getId());
    }

    @Override
    public User search(int id) {
        return users.get(id);
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }
}
