--liquibase formatted sql
--changeset annill:create_tasks_table

CREATE TABLE tasks
(
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255),
    description TEXT,
    status      VARCHAR(50),
    priority    VARCHAR(50),
    author_id   INTEGER REFERENCES users (id) ON DELETE SET NULL,
    executor_id INTEGER REFERENCES users (id) ON DELETE SET NULL,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);