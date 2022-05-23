-- liquibase formatted sql

-- changeset Diana:1
create table "User"
(

    id           serial       NOT NULL PRIMARY KEY,
    chat_id      bigint       NOT NULL,
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'REQUESTED'
);

-- changeset Diana:2
CREATE INDEX user_data_idx ON "User" (user) WHERE status = 'REQUESTED';
