package ru.yandex.practicum.filmorate.controller.storage.user_impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.controller.dao.DaoFriends;
import ru.yandex.practicum.filmorate.controller.dao.DaoUsers;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoFriendsImpl;
import ru.yandex.practicum.filmorate.controller.dao.impl.DaoUsersImpl;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashSet;
import java.util.List;

@Component
@Qualifier("UserStorageDao")
public class UserStorageDao implements UserStorage {
    private final DaoUsers daoUsers;
    private final DaoFriends daoFriends;

    @Autowired
    public UserStorageDao(JdbcTemplate jdbcTemplate) {
        this.daoUsers = new DaoUsersImpl(jdbcTemplate);
        this.daoFriends = new DaoFriendsImpl(jdbcTemplate);
    }

    @Override
    public User addFriend(int idUser, int idFriend) {
        daoFriends.addFriend(idUser, idFriend);
        return search(idUser);
    }

    @Override
    public User deleteFriend(int idUser, int idFriend) {
        daoFriends.deleteFriend(idUser, idFriend);
        return search(idUser);
    }

    @Override
    public List<User> getFriends(int id) {
        return daoFriends.findAllFriendsUser(id);
    }

    @Override
    public List<User> getMutualFriends(int idUser, int idFriend) {
        return daoFriends.getMutualFriends(idUser, idFriend);
    }

    @Override
    public User add(User user) {
        if (user == null) return null;
        if (user.getId() == null) user.setId(1);
        daoUsers.add(user);

        if (user.getFriends() == null) user.setFriends(new HashSet<>());
        if (user.getFriends().isEmpty()) return user;

        for (Integer idFriend : user.getFriends())
            daoFriends.addFriend(user.getId(), idFriend);
        return user;
    }

    @Override
    public User update(User user) {
        if (user == null) return null;
        return daoUsers.update(user);
    }

    @Override
    public User delete(Integer id) {
        User user = search(id);
        if (user == null) return null;

        for (Integer idFriend : user.getFriends())
            daoFriends.deleteFriend(id, idFriend);
        daoUsers.delete(user.getId());
        return user;
    }

    @Override
    public User search(int id) {
        User user = daoUsers.search(id);
        if (user == null) return null;
        if (user.getFriends() == null) user.setFriends(new HashSet<>());

        List<User> friends = daoFriends.findAllFriendsUser(id);
        for (User u : friends) {
            user.getFriends().add(u.getId());
        }
        return user;
    }

    @Override
    public List<User> findAll() {
        return daoUsers.findAll();
    }
}
