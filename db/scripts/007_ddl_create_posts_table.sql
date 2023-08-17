CREATE TABLE if NOT EXISTS posts
(
    id serial PRIMARY KEY,
    file_id int REFERENCES files(id),
    description varchar NOT NULL,
    price int NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    sold BOOLEAN DEFAULT FALSE,
    car_id INT REFERENCES cars(id) NOT NULL,
    user_id INT REFERENCES users(id) NOT NULL,
    UNIQUE (car_id, user_id)
);