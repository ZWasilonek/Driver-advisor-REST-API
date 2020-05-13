create database driving_advisor char set utf8mb4 collate utf8mb4_unicode_ci;

INSERT INTO `roles` VALUES (1, 'ROLE_USER');

INSERT INTO driving_advisor.users (id, enabled, password, username) VALUES ('1', '1', '$2a$10$MYy7btPi24IvAsWn5dUar.azmWH51jCWNF/cLRrL63EH8zJBAYgye', 'user');

