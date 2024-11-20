package org.example;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Properties;

public class Configuration {

    public static StudentRecordMapper getStudentRecordMapper() {
        return new StudentRecordMapper();
    }

    public static StudentDao getStudentDao() {
        return new StudentDao(getStudentRecordMapper());
    }

    public static Optional<Connection> getConnection() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("/Users/ekaterina/batullina-agona-2024/AGONA-03/src/main/recourses/db.properties"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        try {
            return Optional.ofNullable(DriverManager.getConnection(
                    properties.getProperty("db.url"),
                    properties.getProperty("db.username"),
                    properties.getProperty("db.password")));
        } catch (SQLException e) {
            return Optional.empty();
        }
    }
}
