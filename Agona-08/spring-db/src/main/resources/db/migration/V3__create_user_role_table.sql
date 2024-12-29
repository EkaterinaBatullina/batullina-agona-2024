CREATE TABLE user_role (
                           user_id   UUID,
                           role_id   UUID,
                           ---------------
                           CONSTRAINT user_role_pk PRIMARY KEY (user_id, role_id),
                           CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES "user" (uuid) ON DELETE CASCADE,
                           CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES role (uuid) ON DELETE CASCADE
);
