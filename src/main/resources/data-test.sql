CREATE TABLE IF NOT EXISTS TEACHERS (
  id LONG auto_increment NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
)

INSERT INTO TEACHER (id, name, email, password) VALUES (1, 'Albus Dumbledore', 'teacher0@asd.cp', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (2, 'Minerva McGonagall', 'teacher1@unq.edu.ar', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (3, 'Remus Lupin', 'teacher2@unq.edu.ar', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (4, 'Sybill Trelawney', 'teacher3@unq.edu.ar', 'asd123');
INSERT INTO TEACHER (id, name, email, password) VALUES (5, 'Quirinus Quirrell', 'teacher4@unq.edu.ar', 'asd123');

INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('PARTICULAR', '2023-12-30', '2023-12-31', 2);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-30', '2023-12-30', 4);
INSERT INTO lack (article, begin_date, end_date, teacher_id)
VALUES ('STUDYDAY', '2023-12-29', '2023-12-30', 4);