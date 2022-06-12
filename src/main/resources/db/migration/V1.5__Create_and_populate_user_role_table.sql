CREATE TABLE user_role(
   user_id BIGSERIAL NOT NULL ,
   role_id BIGSERIAL NOT NULL
);

INSERT INTO user_role (user_id, role_id) values (1,1);
INSERT INTO user_role (user_id, role_id) values (2,3);