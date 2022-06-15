CREATE TABLE role(
    id BIGSERIAL PRIMARY KEY NOT NULL,
    name VARCHAR NOT NULL,
    app_user_id BIGINT DEFAULT NULL
);

INSERT INTO role (name, app_user_id) values ('admin', 1);
INSERT INTO role (name, app_user_id) values ('user', 2);