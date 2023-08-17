CREATE TABLE if NOT EXISTS price_history
(
    id serial PRIMARY KEY,
    price int NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    post_id INT REFERENCES posts(id)
);