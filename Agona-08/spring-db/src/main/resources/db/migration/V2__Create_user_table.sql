CREATE TABLE "user" (
                        uuid    UUID NOT NULL,
                        name    VARCHAR NOT NULL,
                        email   VARCHAR NOT NULL,
                        ---------------------------
                        CONSTRAINT user_uuid_pk PRIMARY KEY (uuid),
                        CONSTRAINT user_name_uq UNIQUE (name)
);