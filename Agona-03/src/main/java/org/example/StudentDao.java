package org.example;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDao extends AbstractDao<Student> {
    //language=sql
    private static final String SQL_FIND_BY_ID = "SELECT * FROM student WHERE id = ?";
    private static final String SQL_FIND_ALL = "SELECT * FROM student";
    private static final String SQL_INSERT = "INSERT INTO student(name, birth_date, age, gpa, is_active) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_DELETE = "DELETE FROM student WHERE id = ?";
    private static final String SQL_FIND_COURSES_FOR_STUDENT = """ 
        SELECT c.name 
        FROM course c
        JOIN student_course sc ON c.id = sc.course_id
        WHERE sc.student_id = ? 
    """;

    public StudentDao(RowMapper<Student> mapper) {
        this.mapper = mapper;
    }

    List<Student> getAll() {
        try {
            PreparedStatement statement = getConnection().map(connection -> {
                try {
                    return connection.prepareStatement(SQL_FIND_ALL);
                } catch (SQLException e) {
                    throw new StudentException("Error preparing getAll statement");
                }
            }).orElseThrow(StudentException::new);
            ResultSet resultSet = statement.executeQuery();
            List<Student> students = new ArrayList<>();
            while (resultSet.next()) {
                students.add(mapper.mapRow(resultSet));
            }
            return students;
        } catch (SQLException e) {
            throw new StudentException("Can't execute getAll query");
        }
    }

    Optional<Student> findById(String id) {
        try {
            PreparedStatement statement = getConnection().map(connection -> {
                try {
                    PreparedStatement stmt = connection.prepareStatement(SQL_FIND_BY_ID);
                    stmt.setLong(1, Long.parseLong(id));
                    return stmt;
                } catch (SQLException e) {
                    throw new StudentException("Error preparing findById statement for id = %s".formatted (id));
                }
            }).orElseThrow(StudentException::new);
            ResultSet result = statement.executeQuery();
            return result.next() ?
                    Optional.ofNullable(mapper.mapRow(statement.executeQuery())) : Optional.empty();
        } catch (SQLException e) {
            throw new StudentException("Error executing findById query for id = %s".formatted (id));
        }
    }

    boolean deleteById(Long id) {
        try {
            PreparedStatement statement = getConnection().map(connection -> {
                try {
                    PreparedStatement stmt = connection.prepareStatement(SQL_DELETE);
                    stmt.setLong(1, id);
                    return stmt;
                } catch (SQLException e) {
                    throw new StudentException("Error preparing deleteById statement");
                }
            }).orElseThrow(StudentException::new);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new StudentException("Error executing deleteById query for id = %s".formatted (id));
        }
    }

    void save(Student entity) {
        try {
            PreparedStatement statement = getConnection().map(connection -> {
                try {
                    PreparedStatement stmt = connection.prepareStatement(SQL_INSERT);
                    stmt.setString(1, entity.name());
                    stmt.setDate(2, entity.birthDate());
                    stmt.setInt(3, entity.age());
                    stmt.setFloat(4, entity.gpa());
                    stmt.setBoolean(5, entity.isActive());
                    return stmt;
                } catch (SQLException var3) {
                    throw new StudentException("Error preparing save statement");
                }
            }).orElseThrow(StudentException::new);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected <= 0) {
                throw new StudentException("Save failed");
            }
        } catch (SQLException e) {
            throw new StudentException("Error executing save query");
        }
    }

    List<String> getCoursesForStudent(Long studentId) {
        try {
            PreparedStatement statement = getConnection().map(connection -> {
                try {
                    PreparedStatement stmt = connection.prepareStatement(SQL_FIND_COURSES_FOR_STUDENT);
                    stmt.setLong(1, studentId);
                    return stmt;
                } catch (SQLException var3) {
                    throw new StudentException("Error preparing getCoursesForStudent statement");
                }
            }).orElseThrow(StudentException::new);
            ResultSet resultSet = statement.executeQuery();
            List<String> courses = new ArrayList<>();
            while (resultSet.next()) {
                courses.add(resultSet.getString("name"));
            }
            return courses;
        } catch (SQLException e) {
            throw new StudentException("Error executing getCoursesForStudent query");
        }
    }

}