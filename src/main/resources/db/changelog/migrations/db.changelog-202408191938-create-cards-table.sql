--liquibase formatted sql
--changeset junior:202408191938
--comment: create CARDS table to store task cards within board columns

CREATE TABLE CARDS
(
    id              BIGSERIAL PRIMARY KEY,
    title           VARCHAR(255) NOT NULL,
    description     VARCHAR(255) NOT NULL,
    board_column_id BIGINT       NOT NULL,
    CONSTRAINT boards_columns__cards_fk FOREIGN KEY (board_column_id) REFERENCES BOARDS_COLUMNS (id) ON DELETE CASCADE
)

--rollback DROP TABLE CARDS
