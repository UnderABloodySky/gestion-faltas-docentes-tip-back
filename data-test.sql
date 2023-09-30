CREATE TABLE IF NOT EXISTS TEACHER (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

INSERT INTO TEACHER (id, name, email, password) VALUES (999, 'teacher1', 'teacher1@unq.edu.ar', 'teacher1pass');
INSERT INTO TEACHER (name, email, password) VALUES ('teacher2', 'teacher2@unq.edu.ar', 'teacher2pass');
INSERT INTO TEACHER (name, email, password) VALUES ('teacher2', 'teacher2@unq.edu.ar', 'teacher2pass');



INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('PARTICULAR', '2023-12-30', '2023-12-31', 1);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-30', '2023-12-30', 5);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-29', '2023-12-30', 5);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('PARTICULAR', '2023-12-1', '2023-12-31', 3);
