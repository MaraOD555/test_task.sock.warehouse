-- liquibase formatted sql

--changeset mara:1
CREATE TABLE socks
(
    id          SERIAL PRIMARY KEY,
    color       VARCHAR(255),
    cotton_part INT,
    quantity    INT
);