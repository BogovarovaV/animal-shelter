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

-- changeset Diana:2
CREATE INDEX user_data_idx ON Client (user) WHERE status = 'USER';

--changeset Diana:3
CREATE TABLE Dogs_adopters
(
    id           serial       NOT NULL PRIMARY KEY,
    chat_id      bigint       NOT NULL,
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'DOG_ADOPTER'
);

--changeset Diana:4
CREATE INDEX user_data_idx ON Dogs_adopters (user) WHERE status = 'DOG_ADOPTER';

--changeset Diana:5
CREATE TABLE Cats_adopters
(
    id           serial       NOT NULL PRIMARY KEY,
    chat_id      bigint       NOT NULL,
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'CAT_ADOPTER'
);

--changeset Diana:4
CREATE INDEX user_data_idx ON Cats_adopters (user) WHERE status = 'CAT_ADOPTER';