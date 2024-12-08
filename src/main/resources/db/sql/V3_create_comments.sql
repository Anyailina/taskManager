--liquibase formatted sql
--changeset annill:create_comments_table

CREATE TABLE comments
(
    id         SERIAL PRIMARY KEY,
    content    TEXT,
    task_id    INTEGER REFERENCES tasks (id) ON DELETE SET NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
