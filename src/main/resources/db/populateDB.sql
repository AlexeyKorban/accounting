DELETE FROM user_roles;
DELETE FROM projects;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001),
  ('ROLE_USER', 100001);

INSERT INTO projects (date_time, description, sum, user_id) VALUES
  ('2018-10-26 10:00:00', 'МРСК', 500, 100000),
  ('2018-10-26 11:00:00', 'Ростелеком', 500, 100000),
  ('2018-10-27 10:00:00', 'ДИЛ', 500, 100000),
  ('2018-10-27 10:10:00', 'Юнит', 500, 100000),
  ('2018-10-27 11:00:00', 'Софтланй', 500, 100000),
  ('2018-10-27 11:10:00', 'Изделия и материалы', 500, 100000),
  ('2018-10-26 10:00:00', 'Проект Админа', 500, 100001),
  ('2018-10-26 20:00:00', 'Проект 2 Админа', 500, 100001);