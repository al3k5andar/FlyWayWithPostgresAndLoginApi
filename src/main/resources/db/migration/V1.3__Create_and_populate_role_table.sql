CREATE TABLE role(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL
);

INSERT INTO role (name) values ('admin');
INSERT INTO role (name) values ('manager');
INSERT INTO role (name) values ('user');