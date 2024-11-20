package org.example;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        StudentDao studentDao = Configuration.getStudentDao();

        Student newStudent1 = new Student(
                null,
                "John Smith",
                Date.valueOf("2000-01-01"),
                24,
                3.7F,
                true);
        Student newStudent2 = new Student(null,
                "Mary Brown",
                Date.valueOf("2005-06-16"),
                19,
                4.7F,
                true);
        Student newStudent3 = new Student(null,
                "Robert Jones",
                Date.valueOf("2004-03-14"),
                20,
                4.3F,
                false);

        studentDao.save(newStudent1);
        studentDao.save(newStudent2);
        studentDao.save(newStudent3);

        System.out.println("Все студенты:");
        List<Student> students = studentDao.getAll();
        if (students.isEmpty()) {
            System.out.println("Студенты не найдены");
        } else {
            students.forEach(student -> {
                System.out.println("Name: " + student.name());
            });
        }
        //Все студенты:
        //Name: John Smith
        //Name: Mary Brown
        //Name: Robert Jones

        System.out.println("Поиск студента с ID 100400:");
        studentDao.findById("100400").ifPresentOrElse(
                student -> {
                    System.out.println("Name: " + student.name());
                },
                () -> System.out.println("Студент с ID 100400 не найден")
        );
        //Поиск студента с ID 100400:
        //Name: Mary Brown

        System.out.println("Удаление студента с ID 100350:");
        boolean deleted = studentDao.deleteById(100350L);
        if (deleted) {
            System.out.println("Студент успешно удален");
        } else {
            System.out.println("Ошибка удаления");
        }
        //Удаление студента с ID 100350:
        //Студент успешно удален

        System.out.println("Курсы для студента с ID 100400:");
        List<String> courses = studentDao.getCoursesForStudent(100400L);
        if (courses.isEmpty()) {
            System.out.println("Студент не записан на курсы.");
        } else {
            courses.forEach((course) -> {
                System.out.println(course);
            });
        }
        //Курсы для студента с ID 100400:
        //Mathematics
        //Physics
        //Computer Science
    }

}
