DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO "public"."meals" ("id", "userid", "datetime", "description", "calories")
VALUES (DEFAULT, 100000, '2020-06-21 12:47:11', 'first user; first meal', 999);

INSERT INTO "public"."meals" ("id", "userid", "datetime", "description", "calories")
VALUES (DEFAULT, 100000, '2020-06-21 12:47', 'first user; second meal', 999);