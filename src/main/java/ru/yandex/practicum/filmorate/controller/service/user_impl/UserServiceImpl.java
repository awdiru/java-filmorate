package ru.yandex.practicum.filmorate.controller.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.controller.service.UserService;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserServiceImpl(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @Override
    public User addFriend(int idUser, int idFriend) throws IncorrectIdException {
        User user = userStorage.search(idUser);
        User friend = userStorage.search(idFriend);

        if (user == null) throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден");
        if (friend == null) throw new IncorrectIdException("Друг с ID " + idFriend + " не найден");

        if (friend.getFriends() == null) friend.setFriends(new HashSet<>());
        Set<Integer> newFriend = friend.getFriends();
        newFriend.add(user.getId());

        if (user.getFriends() == null) user.setFriends(new HashSet<>());
        newFriend = user.getFriends();
        newFriend.add(friend.getId());

        return user;
    }

    @Override
    public User deleteFriend(int idUser, int idFriend) throws IncorrectIdException {
        User user = userStorage.search(idUser);
        User friend = userStorage.search(idFriend);

        if (user == null) throw new IncorrectIdException("Пользователь с ID " + idUser + " не найден");
        if (friend == null) throw new IncorrectIdException("Друг с ID " + idFriend + " не найден");

        if (user.getFriends() == null) user.setFriends(new HashSet<>());
        user.getFriends().remove(friend.getId());

        if (friend.getFriends() == null) friend.setFriends(new HashSet<>());
        friend.getFriends().remove(user.getId());

        return user;
    }

    @Override
    public List<User> getFriends(int id) throws IncorrectIdException {
        User user = userStorage.search(id);
        if (user == null) throw new IncorrectIdException("Пользователь с ID " + id + " не найден");

        List<User> getFriends = new ArrayList<>();
        Set<Integer> friendsId = user.getFriends();
        if (friendsId == null) return null;

        for (User u : userStorage.findAll()) {
            if (friendsId.contains(u.getId())) getFriends.add(u);
        }

        return getFriends;
    }

    @Override
    public List<User> getMutualFriends(int idFriend1, int idFriend2) throws IncorrectIdException {
        User friend1 = userStorage.search(idFriend1);
        User friend2 = userStorage.search(idFriend2);

        if (friend1 == null) throw new IncorrectIdException("Пользователь с ID " + idFriend1 + " не найден");
        if (friend2 == null) throw new IncorrectIdException("Пользователь с ID " + idFriend2 + " не найден");

        if (friend1.getFriends() == null) friend1.setFriends(new HashSet<>());
        if (friend2.getFriends() == null) friend2.setFriends(new HashSet<>());

        Set<Integer> mutualFriends = friend1.getFriends();
        mutualFriends.retainAll(friend2.getFriends());

        List<User> getFriends = new ArrayList<>();
        for (Integer id : mutualFriends) {
            User friend = userStorage.search(id);
            if (friend != null)
                getFriends.add(friend);
        }

        return getFriends;
    }

    @Override
    public User add(User user) {
        return userStorage.add(user);
    }

    @Override
    public User update(User user) {
        return userStorage.update(user);
    }

    @Override
    public User delete(User user) {
        return userStorage.delete(user);
    }

    @Override
    public User search(int id) {
        return userStorage.search(id);
    }

    @Override
    public List<User> findAll() {
        return userStorage.findAll();
    }
}
