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

INSERT INTO "public"."meals" ("id", "user_id", "date_time", "description", "calories")
VALUES (DEFAULT, 100000, '2020-06-21 12:47', 'first user; first meal', 998),
       (DEFAULT, 100000, '2020-06-21 23:59', 'first user; second meal', 999),
       (DEFAULT, 100000, '2020-06-22 00:00', 'first user; third meal', 999),
       (DEFAULT, 100000, '2020-06-22 23:59', 'first user; fourth meal', 999),


       (DEFAULT, 100001, '2020-06-21 12:45', 'second user; first meal', 997),
       (DEFAULT, 100001, '2020-06-21 23:59', 'second user; second meal', 996),
       (DEFAULT, 100001, '2020-06-22 00:00', 'second user; third meal', 996),
       (DEFAULT, 100001, '2020-06-22 23:59', 'second user; fourth meal', 996);

