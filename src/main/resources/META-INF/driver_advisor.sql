# create database driver_advisor char set utf8mb4 collate utf8mb4_unicode_ci;

INSERT INTO roles VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO users(enabled, password, username, email) VALUES (1,'$2a$10$jklI9ReiNRA4wSW6pNfKB.WrntRF0VihqXPCYX5OpEqQRAjG3OvFO', 'admin', 'admin@gmail');
INSERT INTO users(enabled, password, username, email) VALUES (1,'$2a$10$kdadbv4MyqzKYKCSmE41Xu7hAc2z/Vr1n88lZwhAYq1zHvno7AfAe', 'user', 'user@gmail');

INSERT INTO user_role VALUES (1, 2), (2, 1);