package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.controller.storage.user_impl.UserStorageDao;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserStorageDaoTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testAddAndSearchById() throws IncorrectIdException {
        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        User sevedUser = userStorage.search(1);
        assertThat(sevedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    public void testUpdateUser() throws IncorrectIdException {
        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        user.setName("newUserName");
        user.setLogin("newUserLogin");
        userStorage.update(user);

        User sevedUser = userStorage.search(1);
        assertThat(sevedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user);
    }

    @Test
    public void testDeleteUser() throws IncorrectIdException {
        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        userStorage.delete(user.getId());
        User sevedUser = userStorage.search(1);
        assertThat(sevedUser).isNull();
    }

    @Test
    public void testFindAllUsers() {
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        for (int i = 1; i < 5; i++) {
            User user = createUser();
            userStorage.add(user);
        }

        List<User> users = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            User user = createUser();
            user.setId(i);
            users.add(user);
        }

        List<User> searchUsers = userStorage.findAll();
        assertThat(searchUsers)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(users);
    }

    @Test
    public void testAddFriend() throws IncorrectIdException {
        User user1 = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user1);

        User user2 = createUser();
        userStorage.add(user2);

        userStorage.addFriend(1, 2);

        user1 = userStorage.search(1);
        User sevedUser = createUser();
        sevedUser.getFriends().add(2);

        assertThat(sevedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user1);
    }

    @Test
    public void testDeleteFriend() throws IncorrectIdException {
        User user1 = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user1);

        User user2 = createUser();
        userStorage.add(user2);

        userStorage.addFriend(1, 2);
        userStorage.deleteFriend(1, 2);

        user1 = userStorage.search(1);
        User sevedUser = createUser();

        assertThat(sevedUser)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(user1);

    }

    @Test
    public void testGetFriendsUser() throws IncorrectIdException {
        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        for (int i = 2; i < 5; i++) {
            User friend = createUser();
            userStorage.add(friend);
            userStorage.addFriend(1, i);
        }
        List<User> friends = new ArrayList<>();
        for (int i = 2; i < 5; i++) {
            User friend = createUser();
            friend.setId(i);
            friend.getFriends().add(1);
            friends.add(friend);
        }
        List<User> searchFriends = userStorage.getFriends(1);

        assertThat(searchFriends)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(friends);
    }

    @Test
    public void testGetMutualFriendsUser() throws IncorrectIdException {
        User user1 = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user1);

        User user2 = createUser();
        userStorage.add(user2);

        for (int i = 3; i < 6; i++) {
            User mutualFriend = createUser();
            userStorage.add(mutualFriend);
            userStorage.addFriend(1, i);
            userStorage.addFriend(2, i);
        }
        User friend1 = createUser();
        userStorage.add(friend1);
        userStorage.addFriend(1, 6);

        User friend2 = createUser();
        userStorage.add(friend2);
        userStorage.addFriend(2, 7);

        List<User> mutualFriends = new ArrayList<>();
        for (int i = 3; i < 6; i++) {
            User mutualFriend = createUser();
            mutualFriend.setId(i);
            mutualFriend.getFriends().add(1);
            mutualFriend.getFriends().add(2);
            mutualFriends.add(mutualFriend);
        }

        List<User> searchMutualFriends = userStorage.getMutualFriends(1, 2);
        assertThat(searchMutualFriends)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(mutualFriends);

    }

    private User createUser() {
        return new User(1, "user@email.com", "user_login", "user_name",
                LocalDate.of(1990, 1, 1), new HashSet<>());
    }
}
