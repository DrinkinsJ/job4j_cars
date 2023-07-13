CREATE TABLE IF NOT EXIST auto_user
(
    id          serial  primary key,
    login       varchar unique      not null,
    password    varchar             not null
);