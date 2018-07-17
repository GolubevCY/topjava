DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, user_id, description, calories) VALUES
  ('2018-07-16 08:06', 100000, 'Ужин', 600),
  ('2018-07-17 15:06', 100000, 'Обед', 1100),
  ('2018-07-17 09:00', 100001, 'Завтрак', 1000);