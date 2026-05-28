CREATE TABLE author_profile (
                                uuid   UUID NOT NULL,
                                name   VARCHAR NOT NULL,
                                ------------------------
                                CONSTRAINT author_profile_uuid_pk PRIMARY KEY (uuid)

);

CREATE TABLE author (
                        uuid        UUID NOT NULL,
                        name        VARCHAR(255) NOT NULL,
                        profile_id  UUID,
                        --------------------------------------
                        CONSTRAINT author_uuid_pk PRIMARY KEY (uuid),
                        CONSTRAINT fk_author_profile FOREIGN KEY (profile_id) REFERENCES author_profile (uuid)
);

CREATE TABLE book (
                      uuid       UUID NOT NULL,
                      title      VARCHAR NOT NULL,
                      author_id  UUID NOT NULL,
                      -------------------------------------
                      CONSTRAINT book_uuid_pk PRIMARY KEY (uuid),
                      CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES author (uuid)
);