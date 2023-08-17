CREATE TABLE if NOT EXISTS cars
(
    id serial PRIMARY KEY,
    model varchar NOT NULL,
    engine_id int REFERENCES engines(id) NOT NULL
);