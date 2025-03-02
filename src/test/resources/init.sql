CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   username VARCHAR(50) NOT NULL,
   email VARCHAR(100) NOT NULL,
   created_date DATE,
   is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE books (
   id SERIAL PRIMARY KEY,
   title VARCHAR(100) NOT NULL,
   author VARCHAR(100) NOT NULL,
   published_date DATE,
   user_id INT REFERENCES users(id)
);