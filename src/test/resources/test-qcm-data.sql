INSERT INTO users (id, firstname, lastname, createdAt, updatedAt, enabled, login, password) VALUES
(1, 'adminRest1', NULL, '2016-02-19 19:46:53', '2016-02-19 19:46:53', 1, 'adminRest1@test.fr', '$2a$10$VI/k.Hu9fD5NXtstbhhfAOAbJvRfGfu4L8sTX5ZHkzPQNIV/Dtk8q');

INSERT INTO roles (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_CANDITATE'),
(3, 'ROLE_RH'),
(4, 'ROLE_ADMIN');

INSERT INTO users_roles (user_id, role_id) VALUES
(1, 1),
(1, 4);

