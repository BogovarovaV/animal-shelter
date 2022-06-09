-- liquibase formatted sql

-- changeset Diana:1

CREATE TABLE animal
(
    id   SERIAL       NOT NULL,
    type VARCHAR(255) NOT NULL DEFAULT 'CAT',
    CONSTRAINT animal_primary_key PRIMARY KEY (type)
);

INSERT INTO animal
values (1, 'CAT');
INSERT INTO animal
values (2, 'DOG');


CREATE TABLE quest
(
    id           serial       NOT NULL,
    chat_id      BIGINT       NOT NULL,
    name         varchar(255) NOT NULL,
    phone_number varchar(255) NOT NULL,
    email        varchar(255) NOT NULL,
    status       varchar(255) NOT NULL DEFAULT 'GUEST',
    animal_type  varchar(255)  REFERENCES animal (type),
    CONSTRAINT cat_shelter_client_primary_key PRIMARY KEY (id)
);

CREATE TABLE dog_adopter
(
    CHECK (animal_type = 'DOG')
) INHERITS (quest);

CREATE TABLE cat_adopter
(
    CHECK (animal_type = 'CAT')
) INHERITS (quest);





















