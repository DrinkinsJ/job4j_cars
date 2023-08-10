create TABLE engine
(
    id   SERIAL PRIMARY KEY,
    name varchar NOT NULL
);

create TABLE car
(
    id        SERIAL PRIMARY KEY,
    name      varchar NOT NULL,
    engine_id int     NOT NULL REFERENCES engine (id)
);

create TABLE owners
(
    id      SERIAL PRIMARY KEY,
    name    varchar NOT NULL,
    user_id int     not null REFERENCES auto_user (id)
);

create TABLE history_owners
(
    id       SERIAL PRIMARY KEY,
    car_id   int not null REFERENCES car (id),
    owner_id int not null REFERENCES owners (id)
);

create TABLE history
(
    id      SERIAL PRIMARY KEY,
    startAt TIMESTAMP NOT NULL,
    endAt   TIMESTAMP NOT NULL
);

alter table auto_post
    add column car_id int REFERENCES car (id);

