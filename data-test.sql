CREATE TABLE IF NOT EXISTS TEACHER (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

INSERT INTO TEACHER (id, name, email, password) VALUES (1, 'Albus Dumbledore', 'teacher0@asd.cp', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (2,'Minerva McGonagall', 'teacher1@howgart.edu', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (3, 'Remus Lupin', 'teacher2@howgart.edu', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (4, 'Sybill Trelawney', 'teacher3@howgart.edu', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (5, 'Quirinus Quirrell', 'teacher4@howgart.edu', 'asd123');



INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('PARTICULAR', '2023-12-30', '2023-12-31', 1);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-30', '2023-12-30', 5);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-29', '2023-12-30', 5);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('PARTICULAR', '2023-12-1', '2023-12-31', 3);
