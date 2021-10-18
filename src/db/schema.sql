CREATE TABLE candidate
(
    id          SERIAL PRIMARY KEY,
    description TEXT,
    created     timestamp,
    done        boolean
);