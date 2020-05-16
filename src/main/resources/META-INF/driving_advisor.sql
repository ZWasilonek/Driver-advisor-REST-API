create database driving_advisor char set utf8mb4 collate utf8mb4_unicode_ci;

INSERT INTO `roles` VALUES (1, 'ROLE_USER');

INSERT INTO driving_advisor.users (id, enabled, password, username) VALUES ('1', '1', '$2a$10$MYy7btPi24IvAsWn5dUar.azmWH51jCWNF/cLRrL63EH8zJBAYgye', 'user');

INSERT INTO question_answers VALUES (1,1),(1,2);
INSERT INTO answers VALUES (null, 'test1', 0);
INSERT INTO answers VALUES (null, 'test2', 1);
INSERT INTO questions VALUES (null, 'question', 1);
SELECT a.id, a.description, a.is_correct FROM question_answers JOIN answers a on question_answers.answer_id = a.id WHERE question_id=1 AND a.is_correct=0;

