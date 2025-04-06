--liquibase formatted sql
--changeset junior:202408191938
--comment: create BOARDS table to store user-created boards with metadata

CREATE TABLE BOARDS
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE BOARDS
