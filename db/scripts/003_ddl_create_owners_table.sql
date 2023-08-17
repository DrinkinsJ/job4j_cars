CREATE TABLE if NOT EXISTS owners
(
    id serial PRIMARY KEY,
    name varchar NOT NULL,
    user_id int REFERENCES users(id) NOT NULL
);