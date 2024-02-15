package ru.job4j.collection;

import java.util.*;

public class FullSearch {
    public Set<String> extractNumber(List<Task> tasks) {
        Set<String> result = new HashSet<>();
        for (Task task : tasks) {
            result.add(task.getNumber());
        }
        return result;
    }
}
