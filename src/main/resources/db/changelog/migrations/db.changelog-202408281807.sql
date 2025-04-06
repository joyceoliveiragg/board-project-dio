--liquibase formatted sql
--changeset junior:202408191938
--comment: Update BLOCKS table to allow nullable unblock_reason, since not all blocks are removed

ALTER TABLE BLOCKS
    ALTER COLUMN unblock_reason DROP NOT NULL;

--rollback
ALTER TABLE BLOCKS
    ALTER COLUMN unblock_reason SET NOT NULL;
