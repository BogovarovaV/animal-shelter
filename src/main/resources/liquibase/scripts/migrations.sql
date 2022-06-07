-- liquibase formatted sql

-- changeset Diana:1
create table Client
(

    id           serial       NOT NULL PRIMARY KEY,
    chat_id      bigint       NOT NULL,
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'USER'
);

-- changeset Ann:1

ALTER TABLE Client
    ADD CONSTRAINT chat_id_unique UNIQUE (chat_id);

CREATE TABLE report
(
    id           serial       NOT NULL PRIMARY KEY,
    user_chat_id BIGINT       NOT NULL REFERENCES Client (chat_id),
    report_text  TEXT         NOT NULL,
    file_path    TEXT         NOT NULL,
    sent_date    TIMESTAMP WITH TIME ZONE,
    status       varchar(255) NOT NULL DEFAULT 'DECLINED'
);

-- changeset Diana:2

CREATE TABLE Dog_adopters

(
    id           serial       NOT NULL PRIMARY KEY,
    user_chat_id BIGINT       NOT NULL REFERENCES Client (chat_id),
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'REQUESTED',
    animal_type  varchar(255) NOT NULL DEFAULT 'DOG'
);

-- changeset Diana:3

CREATE INDEX Dog_adopters_index on Dog_adopters (animal_type);

-- changeset Diana:4

CREATE TABLE Cat_adopters

(
    id           serial       NOT NULL PRIMARY KEY,
    user_chat_id BIGINT       NOT NULL REFERENCES Client (chat_id),
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'REQUESTED',
    animal_type  varchar(255) NOT NULL DEFAULT 'CAT'
);

-- changeset Diana:5

CREATE INDEX Cat_adopters_index on Cat_adopters (animal_type);







