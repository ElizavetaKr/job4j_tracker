package ru.job4j.lombok;

public class LombokUsage {
    public static void main(String[] args) {
        Permission permission = Permission.myBuilderName()
                .id(1)
                .name("Name")
                .rules("Rule1")
                .rules("Rule1")
                .rules("Rule1")
                .build();
        System.out.println(permission);
    }
}
