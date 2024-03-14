package ru.job4j.collection.departments;

import java.util.Comparator;

public class DepartmentsDescComparator implements Comparator<String> {
    @Override
    public int compare(String left, String right) {
        int result = right.split("/")[0].compareTo(left.split("/")[0]);
        return result != 0 ? result : left.compareTo(right);
    }
}