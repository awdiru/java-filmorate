package ru.yandex.practicum.filmorate.controller.service.user_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

@Service
@Qualifier("UserServiceImpl")
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(@Qualifier("UserStorageDao") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addFriend(int idUser, int idFriend) throws IncorrectIdException {
        search(idUser);
        search(idFriend);
        return userStorage.addFriend(idUser, idFriend);
    }

    @Override
    public User deleteFriend(int idUser, int idFriend) throws IncorrectIdException {
        search(idUser);
        search(idFriend);
        return userStorage.deleteFriend(idUser, idFriend);
    }

    @Override
    public List<User> getFriends(int id) throws IncorrectIdException {
        search(id);
        return userStorage.getFriends(id);
    }

    @Override
    public List<User> getMutualFriends(int idUser, int idFriend) throws IncorrectIdException {
        search(idUser);
        search(idFriend);

        return userStorage.getMutualFriends(idUser, idFriend);
    }

    @Override
    public User add(User user) {
        return userStorage.add(user);
    }

    @Override
    public User update(User user) throws IncorrectIdException {
        search(user.getId());
        return userStorage.update(user);
    }

    @Override
    public User delete(Integer id) throws IncorrectIdException {
        search(id);
        return userStorage.delete(id);
    }

    @Override
    public User search(int id) throws IncorrectIdException {
        User user = userStorage.search(id);
        if (user == null)
            throw new IncorrectIdException("Пользователь с id " + id + " не найден.");
        return user;
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }

    @Override
    public List<Film> getRecommendations(int id) throws IncorrectIdException {
        search(id);
        return userStorage.getRecommendations(id);
    }
}
