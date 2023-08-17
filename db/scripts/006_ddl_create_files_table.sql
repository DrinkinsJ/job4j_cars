CREATE TABLE if NOT EXISTS files
(
    id serial PRIMARY KEY,
    path VARCHAR NOT NULL,
    file_name VARCHAR NOT NULL,
    UNIQUE (path, file_name)
);