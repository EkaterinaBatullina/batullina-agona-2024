package org.example;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDao<T> {
    RowMapper<T> mapper;

    abstract List<T> getAll();

    abstract Optional<T> findById(String id);

    abstract boolean deleteById(Long id);

    abstract boolean save(Student student);

    abstract List<String> getCoursesForStudent(Long id);

    public Optional<Connection> getConnection() {
        return Configuration.getConnection();
    }
}
