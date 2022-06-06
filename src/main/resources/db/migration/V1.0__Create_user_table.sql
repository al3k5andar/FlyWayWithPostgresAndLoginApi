CREATE TABLE users (
    id BIGSERIAL primary key not null,
    username varchar(45) not null unique,
    password varchar(255) not null,
    email varchar(50),
    user_status text not null
);