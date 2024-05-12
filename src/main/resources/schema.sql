DROP TABLE IF EXISTS PUBLIC.REVIEW_LIKES;
DROP TABLE IF EXISTS PUBLIC.REVIEW_DISLIKES;
DROP TABLE IF EXISTS PUBLIC.REVIEWS;
DROP TABLE IF EXISTS PUBLIC.FEED;
DROP TABLE IF EXISTS PUBLIC.EVENT_TYPES;
DROP TABLE IF EXISTS PUBLIC.OPERATIONS;
DROP TABLE IF EXISTS PUBLIC.FRIENDS;
DROP TABLE IF EXISTS PUBLIC.LIKES;
DROP TABLE IF EXISTS PUBLIC.FILM_GENRE;
DROP TABLE IF EXISTS PUBLIC.FILM_DIRECTOR;
DROP TABLE IF EXISTS PUBLIC.FILMS;
DROP TABLE IF EXISTS PUBLIC.USERS;
DROP TABLE IF EXISTS PUBLIC.RATINGS;
DROP TABLE IF EXISTS PUBLIC.GENRES;
DROP TABLE IF EXISTS PUBLIC.DIRECTORS;

CREATE TABLE PUBLIC.USERS (
	USER_ID INTEGER NOT NULL AUTO_INCREMENT,
	EMAIL VARCHAR_IGNORECASE(200) NOT NULL,
	LOGIN VARCHAR_IGNORECASE(200) NOT NULL,
	NAME VARCHAR_IGNORECASE(200) NOT NULL,
	BIRTHDAY DATE,
	CONSTRAINT USERS_PK PRIMARY KEY (USER_ID)
);

CREATE TABLE PUBLIC.GENRES (
	GENRE_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR_IGNORECASE(200) NOT NULL,
	CONSTRAINT GENRES_PK PRIMARY KEY (GENRE_ID)
);

CREATE TABLE PUBLIC.RATINGS (
	RATING_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR_IGNORECASE(200) NOT NULL,
	CONSTRAINT RATINGS_PK PRIMARY KEY (RATING_ID)
);

CREATE TABLE PUBLIC.FILMS (
	FILM_ID INTEGER NOT NULL AUTO_INCREMENT,
	NAME VARCHAR_IGNORECASE(200) NOT NULL,
	DESCRIPTION VARCHAR_IGNORECASE(200) NOT NULL,
	RELEASE_DATE DATE,
	DURATION INTEGER,
	RATING_ID INTEGER,
	CONSTRAINT FILMS_PK PRIMARY KEY (FILM_ID),
	CONSTRAINT FILMS_RATINGS_FK FOREIGN KEY (RATING_ID) REFERENCES PUBLIC.RATINGS(RATING_ID) ON DELETE SET NULL ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.FRIENDS (
	USER_ID INTEGER NOT NULL,
	FRIEND_ID INTEGER NOT NULL,
	CONSTRAINT FRIENDS_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
	CONSTRAINT FRIENDS_USERS_FK_1 FOREIGN KEY (FRIEND_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.LIKES (
	FILM_ID INTEGER NOT NULL,
	USER_ID INTEGER NOT NULL,
	CONSTRAINT LIKES_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
	CONSTRAINT LIKES_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.FILM_GENRE (
	FILM_ID INTEGER NOT NULL,
	GENRE_ID INTEGER NOT NULL,
	CONSTRAINT FILM_GENRE_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
	CONSTRAINT FILM_GENRE_GENRES_FK FOREIGN KEY (GENRE_ID) REFERENCES PUBLIC.GENRES(GENRE_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.REVIEWS (
REVIEW_ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
CONTENT VARCHAR_IGNORECASE(300) NOT NULL,
IS_POSITIVE BOOL NOT NULL,
USER_ID INTEGER NOT NULL,
FILM_ID INTEGER NOT NULL,
CONSTRAINT REVIEWS_USER_ID_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
CONSTRAINT REVIEWS_FILM_ID_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.REVIEW_LIKES (
REVIEW_ID INTEGER NOT NULL,
USER_ID INTEGER NOT NULL,
PRIMARY KEY (REVIEW_ID, USER_ID),
CONSTRAINT REVIEW_LIKES_REVIEWS_FK FOREIGN KEY (REVIEW_ID) REFERENCES PUBLIC.REVIEWS(REVIEW_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
CONSTRAINT REVIEW_LIKES_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.REVIEW_DISLIKES (
REVIEW_ID INTEGER NOT NULL,
USER_ID INTEGER NOT NULL,
PRIMARY KEY (REVIEW_ID, USER_ID),
CONSTRAINT REVIEW_DISLIKES_REVIEWS_FK FOREIGN KEY (REVIEW_ID) REFERENCES PUBLIC.REVIEWS(REVIEW_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
CONSTRAINT REVIEW_DISLIKES_USERS_FK FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.DIRECTORS (
    DIRECTOR_ID INTEGER NOT NULL AUTO_INCREMENT,
    NAME VARCHAR_IGNORECASE(200) NOT NULL,
    CONSTRAINT DIRECTORS_PK PRIMARY KEY (DIRECTOR_ID)
);

CREATE TABLE PUBLIC.FILM_DIRECTOR (
    FILM_ID INTEGER NOT NULL,
    DIRECTOR_ID INTEGER NOT NULL,
    CONSTRAINT FILM_DIRECTOR_FILMS_FK FOREIGN KEY (FILM_ID) REFERENCES PUBLIC.FILMS(FILM_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT FILM_DIRECTOR_DIRECTORS_FK FOREIGN KEY (DIRECTOR_ID) REFERENCES PUBLIC.DIRECTORS(DIRECTOR_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);

CREATE TABLE PUBLIC.EVENT_TYPES (
EVENT_TYPE_ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
NAME VARCHAR_IGNORECASE(100) NOT NULL
);

CREATE TABLE PUBLIC.OPERATIONS (
OPERATION_ID INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
NAME VARCHAR_IGNORECASE(100) NOT NULL
);

CREATE TABLE PUBLIC.FEED (
EVENT_ID INTEGER NOT NULL GENERATED BY DEFAULT AS identity PRIMARY KEY,
USER_ID INTEGER NOT NULL,
ENTITY_ID INTEGER NOT NULL,
EVENT_TYPE_ID INTEGER NOT NULL,
OPERATION_ID INTEGER NOT NULL,
TIMESTAMP TIMESTAMP NOT NULL,
CONSTRAINT feed_users_fk FOREIGN KEY (USER_ID) REFERENCES PUBLIC.USERS(USER_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
CONSTRAINT feed_event_types_fk FOREIGN KEY (EVENT_TYPE_ID) REFERENCES PUBLIC.EVENT_TYPES(EVENT_TYPE_ID) ON DELETE CASCADE ON UPDATE RESTRICT,
CONSTRAINT feed_OPERATION_fk FOREIGN KEY (OPERATION_ID) REFERENCES PUBLIC.OPERATIONS(OPERATION_ID) ON DELETE CASCADE ON UPDATE RESTRICT
);