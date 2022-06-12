CREATE TABLE role_permission(
      role_id BIGSERIAL NOT NULL,
      permission_id BIGSERIAL NOT NULL
);

INSERT INTO role_permission (role_id, permission_id) values (1,1);
INSERT INTO role_permission (role_id, permission_id) values (1,2);
INSERT INTO role_permission (role_id, permission_id) values (3,1);