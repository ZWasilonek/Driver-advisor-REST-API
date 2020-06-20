# create database driver_advisor char set utf8mb4 collate utf8mb4_unicode_ci;
SET @@auto_increment_increment=1;
set @@auto_increment_offset = 1;

INSERT INTO roles(role) VALUES ('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users(enabled, password, username, email) VALUES (1,'$2a$10$jklI9ReiNRA4wSW6pNfKB.WrntRF0VihqXPCYX5OpEqQRAjG3OvFO', 'admin', 'admin@gmail');
INSERT INTO users(enabled, password, username, email) VALUES (1,'$2a$10$kdadbv4MyqzKYKCSmE41Xu7hAc2z/Vr1n88lZwhAYq1zHvno7AfAe', 'user', 'user@gmail');

INSERT INTO user_role VALUES (1, 2), (1, 1), (2, 1);