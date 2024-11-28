package org.example;

import java.sql.Date;

public record Student(
        Long id,
        String name,
        Date birthDate,
        Integer age,
        Float gpa,
        Boolean isActive) {}
