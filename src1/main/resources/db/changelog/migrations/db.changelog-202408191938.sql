--liquibase formatted sql
--changeset junior:202408191938
--comment: boards table create

CREATE TABLE BOARDS(
    id BIGINT SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
)

--rollback DROP TABLE BOARDS