create database driving_advisor char set utf8mb4 collate utf8mb4_unicode_ci;

INSERT INTO `roles` VALUES (1, 'ROLE_USER'), (2, 'ROLE_ADMIN');

INSERT INTO driving_advisor.users (id, enabled, password, username) VALUES ('1', '1', '$2a$10$MYy7btPi24IvAsWn5dUar.azmWH51jCWNF/cLRrL63EH8zJBAYgye', 'user');

INSERT INTO question_answers VALUES (1,1),(1,2);
INSERT INTO answers VALUES (null, 'test1', 0, 1);
INSERT INTO answers VALUES (null, 'test2', 1, 2);
