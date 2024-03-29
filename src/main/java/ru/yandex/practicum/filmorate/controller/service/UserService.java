package ru.yandex.practicum.filmorate.controller.service;

import ru.yandex.practicum.filmorate.controller.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.List;

public interface UserService extends UserStorage {
    User addFriend(int idUser, int idFriend) throws IncorrectIdException;

    User deleteFriend(int idUser, int idFriend) throws IncorrectIdException;

    List<User> getFriends(int id) throws IncorrectIdException;

    List<User> getMutualFriends(int idFriend1, int idFriend2) throws IncorrectIdException;
}
