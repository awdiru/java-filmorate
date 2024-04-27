package ru.yandex.practicum.filmorate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import javax.validation.Validator;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTests {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void userWithoutErrors() {
        User user = new User(0, "user@email.com", "loginUser", "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertTrue(violations.isEmpty());
    }

    @Test
    void userIdNull() {
        User user = new User(null, "user@email.com", "loginUser", "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertTrue(violations.isEmpty());
    }

    @Test
    void userLoginIncorrect() {
        User user = new User(0, "user@email.com", "loginU ser", "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userLoginNull() {
        User user = new User(0, "user@email.com", null, "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userEmailIncorrect() {
        User user = new User(0, "null", "userLogin", "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userEmailNull() {
        User user = new User(0, null, "userLogin", "userName",
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userBirthdayIncorrect() {
        User user = new User(0, "user@email.com", "userLogin", "userName",
                LocalDate.of(2030, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userBirthdayNull() {
        User user = new User(0, "user@email.com", "userLogin", "userName",
                null, new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 1);
    }

    @Test
    void userNameNull() {
        User user = new User(0, "user@email.com", "userLogin", null,
                LocalDate.of(2014, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertTrue(violations.isEmpty());
    }

    @Test
    void allIncorrectField() {
        User user = new User(0, "usail.com", "userLo gin", null,
                LocalDate.of(2030, 3, 3), new HashSet<>());
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> u : violations) {
            System.out.println(u + "\n\n");
        }
        assertEquals(violations.size(), 3);
    }
}
