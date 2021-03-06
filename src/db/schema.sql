CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT        not null,
    email    TEXT unique not null,
    password TEXT        not null
);

CREATE TABLE items
(
    id          SERIAL PRIMARY KEY,
    description TEXT                      not null,
    created     timestamp                 not null,
    done        boolean                   not null,
    user_id     int references users (id) not null
);

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name TEXT not null,
);

CREATE TABLE items_categories
(
    item_id       BIGINT NOT NULL,
    categories_id BIGINT NOT NULL
);