# java-filmorate

![ProjectDiargammBD.png](src/main/resources/shema.png)

>[SQL код базы данных](src/main/resources/shema.sql)
***
## Примеры запросов SQL:

### Добавить нового пользователя:
```roomsql
INSERT INTO users (email, login, name, birthday)
VALUES ('email@yandex.ru', 'login', 'user name', '2001-11-01');
```
### Обновить данные пользователя:
```roomsql
UPDATE users 
SET name = 'new name' 
WHERE name = 'user name';
```
### Удаление пользователя с email email@yandex.ru:
```roomsql
DELETE FROM users
WHERE email = 'email@yandex.ru'; 
```
### Найти пользователя с id 5
```roomsql
SELECT * FROM users WHERE users_id = 5;
```
### Добавить друга c id 5 пользователю с id 1
```roomsql
INSERT INTO friends (user_id, friend_user_id)
VALUES (1, 5);
```
### Удалить друга с id 5 у пользователя м id 1
```roomsql
DELETE FROM friends
WHERE 
    user_id = 1
AND 
    friend_user_id = 5;
```
### Показать список друзей пользователя с id 5
```roomsql
SELECT u.name, u.login, u.email, f.confirm 
FROM 
    friends AS f
JOIN
    users AS u ON f.friend_user_id = u.user_id
WHERE
    f.user_id = 5;
```
### Показать список общих друзей пользователей с id 1 и 5
```roomsql
SELECT u.name, u.login, u.email FROM users
WHERE
    user_id IN (
        SELECT friends_user_id FROM friends WHERE user_id = 1
    ) AND user_id IN (
        SELECT friends_user_id FROM friends WHERE user_id = 5
    );
```
***
### Добавить фильм
```roomsql
INSERT INTO films (name, description, releaseDate, duration, rating)
VALUES ('film', 'description', '2011-11-01', 122213, 3);
```
### Обновить данные фильма с id 1
```roomsql
UPDATE films
SET description = 'new description'
WHERE film_id = 1;
```
### Удалить фильм с id 1 из базы данных
```roomsql
DELETE FROM films
WHERE film_id = 1;
```
### Найти фильм с id 1
```roomsql
SELECT * FROM films
WHERE film_id = 1;
``` 
### Добавить лайк фильму с id 1 от пользователя с id 5
```roomsql
INSERT INTO likes (film_id, user_id)
VALUES (1, 5);
```
### Удалить лайк у фильма с id 1 от пользователя с id 5
```roomsql
DELETE FROM likes
WHERE
    film_id = 1
AND 
    user_id = 5;
```
### Показать 10 самых популярных фильмов
```roomsql
SELECT f.name AS film, COUNT(l.user_id) AS likes
FROM
    likes AS l
JOIN 
    films AS f ON l.film_id = f.film_id
GROUP BY film
ORDER BY likes DESC
LIMIT 10;
```