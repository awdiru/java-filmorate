CREATE TABLE "users" (
  "user_id" integer PRIMARY KEY,
  "email" varchar[200],
  "login" varchar[200],
  "name" varchar[200],
  "birthday" date
);

CREATE TABLE "friends" (
  "user_id" integer,
  "friend_user_id" integer,
  "confirm" bool DEFAULT FALSE
);

CREATE TABLE "films" (
  "film_id" integer PRIMARY KEY,
  "name" varchar[200],
  "description" varchar[200],
  "releaseDate" date,
  "duration" integer,
  "rating" integer
);

CREATE TABLE "ratings" (
  "rating_id" integer PRIMARY KEY,
  "name" varchar[200]
);

CREATE TABLE "likes" (
  "user_id" integer,
  "film_id" integer
);

CREATE TABLE "genres" (
  "genre_id" integer PRIMARY KEY,
  "name" varchar[200]
);

CREATE TABLE "film_genre" (
  "film_id" integer,
  "genre_id" integer
);

ALTER TABLE "friends" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "friends" ADD FOREIGN KEY ("friend_user_id") REFERENCES "users" ("user_id");

ALTER TABLE "films" ADD FOREIGN KEY ("rating") REFERENCES "ratings" ("rating_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "likes" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("user_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("film_id") REFERENCES "films" ("film_id");

ALTER TABLE "film_genre" ADD FOREIGN KEY ("genre_id") REFERENCES "genres" ("genre_id");