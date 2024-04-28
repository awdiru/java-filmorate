package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.controller.storage.FilmStorage;
import ru.yandex.practicum.filmorate.controller.storage.UserStorage;
import ru.yandex.practicum.filmorate.controller.storage.film_impl.FilmStorageDao;
import ru.yandex.practicum.filmorate.controller.storage.user_impl.UserStorageDao;
import ru.yandex.practicum.filmorate.exceptions.IncorrectIdException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@JdbcTest
@RequiredArgsConstructor(onConstructor_ = @Autowired)

public class FilmStorageDaoTest {
    private final JdbcTemplate jdbcTemplate;

    @Test
    public void testAddAndGetFilmById() throws IncorrectIdException {
        deleteBd();
        Film film = createFilm();
        FilmStorage filmStorage = new FilmStorageDao(jdbcTemplate);
        filmStorage.add(film);

        Film sevedFilm = filmStorage.search(1);
        assertThat(sevedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }

    @Test
    public void testUpdateFilm() {
        deleteBd();
        Film film = createFilm();
        FilmStorageDao filmStorage = new FilmStorageDao(jdbcTemplate);
        filmStorage.add(film);

        film.setName("newName");
        film.setDescription("newDescription");

        filmStorage.update(film);

        Film sevedFilm = filmStorage.search(1);
        assertThat(sevedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }

    @Test
    public void testDeleteFilm() {
        deleteBd();
        Film film = createFilm();
        FilmStorageDao filmStorage = new FilmStorageDao(jdbcTemplate);
        filmStorage.add(film);

        filmStorage.delete(1);

        Film sevedFilm = filmStorage.search(1);
        assertThat(sevedFilm).isNull();
    }

    @Test
    public void testFindAllFilms() {
        deleteBd();
        FilmStorageDao filmStorage = new FilmStorageDao(jdbcTemplate);
        for (int i = 1; i < 5; i++) {
            Film film = createFilm();
            filmStorage.add(film);
        }

        List<Film> films = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Film film = createFilm();
            film.setId(i);
            films.add(film);
        }

        List<Film> searchFilms = filmStorage.findAll();
        assertThat(searchFilms)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(films);
    }

    @Test
    public void testAddLike() {
        deleteBd();
        Film film = createFilm();
        FilmStorageDao filmStorage = new FilmStorageDao(jdbcTemplate);
        filmStorage.add(film);

        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        filmStorage.addLike(1, 1);
        film.getLikes().add(1);

        Film sevedFilm = filmStorage.search(1);
        assertThat(sevedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }

    @Test
    public void testDeleteLike() {
        deleteBd();
        Film film = createFilm();
        FilmStorageDao filmStorage = new FilmStorageDao(jdbcTemplate);
        filmStorage.add(film);

        User user = createUser();
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);
        userStorage.add(user);

        filmStorage.addLike(1, 1);
        filmStorage.deleteLike(1, 1);

        Film sevedFilm = filmStorage.search(1);
        assertThat(sevedFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(film);
    }

    @Test
    public void testPopFilms() throws IncorrectIdException {
        deleteBd();
        FilmStorage filmStorage = new FilmStorageDao(jdbcTemplate);
        UserStorage userStorage = new UserStorageDao(jdbcTemplate);

        for (int i = 1; i < 6; i++) {
            Film film = createFilm();
            filmStorage.add(film);
        }
        for (int i = 1; i < 6; i++) {
            User user = createUser();
            userStorage.add(user);
        }

        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 7 - i; j++) {
                filmStorage.addLike(i, j);
            }
        }
        filmStorage.addLike(5, 1);

        List<Film> popFilms = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            Film film = createFilm();
            film.setId(i);
            for (int j = 1; j < 7 - i; j++) {
                film.getLikes().add(j);
            }
            popFilms.add(film);
        }
        Film film = createFilm();
        film.setId(5);
        film.getLikes().add(1);
        popFilms.add(film);

        List<Film> searchPopFilm = filmStorage.popFilms(5);
        assertThat(searchPopFilm)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(popFilms);

    }

    private Film createFilm() {
        return new Film(1, "filmName", "filmDescription",
                LocalDate.of(1990, 01, 01), 1000,
                new Rating(1, "G"), new LinkedList<>(), new LinkedList<>());
    }

    private User createUser() {
        return new User(1, "user@email.com", "user_login", "user_name",
                LocalDate.of(1990, 1, 1), new HashSet<>());
    }

    private void deleteBd() {
        String sql = "TRUNCATE TABLE likes;";
        jdbcTemplate.update(sql);
        sql = "TRUNCATE TABLE film_genre;";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM films;";
        jdbcTemplate.update(sql);
        sql = "ALTER TABLE films ALTER COLUMN film_id RESTART WITH 1;";
        jdbcTemplate.update(sql);
        sql = "TRUNCATE TABLE friends;";
        jdbcTemplate.update(sql);
        sql = "DELETE FROM users;";
        jdbcTemplate.update(sql);
        sql = "ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1;";
        jdbcTemplate.update(sql);
    }
}
