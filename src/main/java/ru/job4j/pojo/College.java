package ru.job4j.pojo;

import java.util.Date;

public class College {
    public static void main(String[] args) {
        Student student = new Student();
        student.setFullName("Иванов Иван Иванович");
        student.setGroup(1145);
        student.setAdmission(new Date());
        System.out.println("Студент: " + student.getFullName() + " № группы - " + student.getGroup()
                            + ". Дата поступления: " + student.getAdmission());
    }
}
