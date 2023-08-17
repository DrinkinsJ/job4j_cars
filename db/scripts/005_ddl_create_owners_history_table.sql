CREATE TABLE if NOT EXISTS owners_history
(
    id serial PRIMARY KEY,
    car_id int REFERENCES cars(id) NOT NULL,
    owner_id int REFERENCES owners(id) NOT NULL,
    UNIQUE (car_id, owner_id)
);