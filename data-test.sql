CREATE TABLE IF NOT EXISTS TEACHERS (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

INSERT INTO TEACHERS (name, email, password) VALUES ('teacher1', 'teacher1@unq.edu.ar', 'teacher1');
INSERT INTO TEACHERS (name, email, password) VALUES ('teacher2', 'teacher2@unq.edu.ar', 'teacher2');
