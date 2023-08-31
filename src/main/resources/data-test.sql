CREATE TABLE IF NOT EXISTS TEACHERS (
  id LONG auto_increment NOT NULL,
  name VARCHAR(255) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
)

INSERT INTO TEACHERS (id, name, email, password) VALUES (420, 'teacher1', 'teacher1@unq.edu.ar', 'teacher1');
