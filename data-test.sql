CREATE TABLE IF NOT EXISTS TEACHER (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

INSERT INTO TEACHER (id, name, email, password) VALUES (999, 'teacher1', 'teacher1@unq.edu.ar', 'teacher1pass');
INSERT INTO TEACHER (name, email, password) VALUES ('teacher2', 'teacher2@unq.edu.ar', 'teacher2pass');
