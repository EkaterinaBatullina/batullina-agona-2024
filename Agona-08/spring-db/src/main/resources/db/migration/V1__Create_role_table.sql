CREATE TABLE role (
                      uuid          UUID NOT NULL,
                      name          VARCHAR NOT NULL,
                      description   VARCHAR NOT NULL,
                      -------------------------------
                      CONSTRAINT role_uuid_pk PRIMARY KEY (uuid)
);