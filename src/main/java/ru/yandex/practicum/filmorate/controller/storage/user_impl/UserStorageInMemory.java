package ru.yandex.practicum.filmorate.controller.storage.user_impl;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Component
@Qualifier("UserStorageInMemory")
public class UserStorageInMemory implements UserStorage {
    private final Map<Integer, User> users;
    private int generateId;

    public UserStorageInMemory() {
        users = new HashMap<>();
        generateId = 1;
    }

    @Override
    public User addFriend(int idUser, int idFriend) {
        User user = users.get(idUser);
        user.getFriends().add(idFriend);
        return user;
    }

    @Override
    public User deleteFriend(int idUser, int idFriend) {
        User user = users.get(idUser);
        user.getFriends().remove(idFriend);
        return user;
    }

    @Override
    public List<User> getFriends(int id) {
        User user = users.get(id);
        Set<Integer> idFriends = user.getFriends();

        if (idFriends == null || idFriends.isEmpty()) return new ArrayList<>();

        List<User> friends = new ArrayList<>();
        for (Integer idF : idFriends)
            friends.add(users.get(idF));
        return friends;
    }

    @Override
    public List<User> getMutualFriends(int idUser, int idFriend) {
        Set<Integer> userFriends = users.get(idUser).getFriends();
        userFriends.retainAll(users.get(idFriend).getFriends());

        List<User> mutualFriends = new ArrayList<>();
        for (Integer id : userFriends) {
            mutualFriends.add(users.get(id));
        }
        return mutualFriends;
    }

    @Override
    public User add(User user) {
        for (User u : users.values())
            if (u.getEmail().equals(user.getEmail()))
                return null;

        if (user.getName() == null)
            user.setName(user.getLogin());

        if (user.getFriends() == null)
            user.setFriends(new HashSet<>());

        user.setId(generateId++);
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
    public User delete(Integer id) {
        return users.remove(id);
    }

    @Override
    public User search(int id) {
        return users.get(id);
    }

    @Override
    public List<User> findAll() {
        return List.copyOf(users.values());
    }

    @Override
    public List<Film> getRecommendations(int id) {
        return null;
    }
}
