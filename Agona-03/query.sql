CREATE SEQUENCE student_sequence
    START WITH 100000
    INCREMENT BY 1
    CACHE 50;

CREATE SEQUENCE course_sequence
    START WITH 100000
    INCREMENT BY 1
    CACHE 50;

CREATE TABLE student (
                         id                  BIGINT NOT NULL DEFAULT nextval('student_sequence'),
                         name                VARCHAR NOT NULL,
                         birth_date          DATE,
                         age                 INT,
                         gpa                 FLOAT,
                         is_active           BOOLEAN DEFAULT TRUE,
                         -----------------------------------------------------------
                         CONSTRAINT student_id_pk      PRIMARY KEY(id),
                         CONSTRAINT student_name_uq    UNIQUE (name),
                         CONSTRAINT student_age_ck     CHECK (age >= 17),
                         CONSTRAINT student_gpa_ck     CHECK (gpa >= 0 AND gpa <= 5)
);

comment on column student.gpa is 'Средний балл';
comment on column student.is_active is 'Активен ли студент в образовательном процессе или находится в академическом отпуске';

CREATE TABLE course (
                        id     BIGINT NOT NULL DEFAULT nextval('course_sequence'),
                        name   VARCHAR(255) NOT NULL,
                        hours  INT,
                        -----------------------------------------------
                        CONSTRAINT course_id_pk        PRIMARY KEY (id),
                        CONSTRAINT course_name_uq      UNIQUE (name),
                        CONSTRAINT course_hours_ck     CHECK (hours > 0)
);

INSERT INTO course (name, hours) VALUES
                                     ('Mathematics', 40),
                                     ('Physics', 50),
                                     ('Computer Science', 45),
                                     ('History', 30),
                                     ('Literature', 40);

comment on column course.hours is 'Общее количество часов';

CREATE TABLE student_course (
                                student_id  BIGINT,
                                course_id   BIGINT,
                                FOREIGN KEY (student_id)  REFERENCES student(id),
                                FOREIGN KEY (course_id)   REFERENCES course(id)
);

INSERT INTO student_course (student_id, course_id) 
VALUES 
    (100400, (SELECT id FROM course WHERE name = 'Mathematics')),
    (100400, (SELECT id FROM course WHERE name = 'Physics')),
    (100400, (SELECT id FROM course WHERE name = 'Computer Science'));


INSERT INTO student_course (student_id, course_id) 
VALUES 
    (100450, (SELECT id FROM course WHERE name = 'Mathematics')),
    (100450, (SELECT id FROM course WHERE name = 'History')),
    (100450, (SELECT id FROM course WHERE name = 'Literature'));   
