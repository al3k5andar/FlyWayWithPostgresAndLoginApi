CREATE TABLE permission(
     id BIGSERIAL PRIMARY KEY NOT NULL,
     name VARCHAR NOT NULL
);

INSERT INTO permission (name) values ('read');
INSERT INTO permission (name) values ('write');