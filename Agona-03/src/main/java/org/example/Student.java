package org.example;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Date;

public record Student(
        Long id,
        String name,
        Date birthDate,
        Integer age,
        Float gpa,
        Boolean isActive) {}
