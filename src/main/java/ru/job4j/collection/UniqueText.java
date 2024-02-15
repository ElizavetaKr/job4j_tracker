package ru.job4j.collection;

import java.util.HashSet;

public class UniqueText {
    public boolean isEquals(String originText, String duplicateText) {
        boolean result = true;
        String[] origin = originText.split(" ");
        String[] duplicate = duplicateText.split(" ");
        HashSet<String> check = new HashSet<>();
        for (String str : origin) {
            check.add(str);
        }
        for (String str : duplicate) {
            if (!check.contains(str)) {
                result = false;
                break;
            }
        }
        return result;
    }
}
