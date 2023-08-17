CREATE TABLE if NOT EXISTS car_brands
(
    id serial PRIMARY KEY,
    brand varchar NOT NULL UNIQUE
);