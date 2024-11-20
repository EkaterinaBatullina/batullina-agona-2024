package org.example;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRecordMapper implements RowMapper<Student> {
    public StudentRecordMapper() {
    }

    public Student mapRow(ResultSet resultSet) throws SQLException {
        return new Student(resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDate("birth_date"),
                resultSet.getInt("age"),
                resultSet.getFloat("gpa"),
                resultSet.getBoolean("is_active"));
    }

}
