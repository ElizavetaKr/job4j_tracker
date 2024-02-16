package ru.job4j.collection;

import java.util.HashMap;

public class UsageMap {
    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("mail1@mail.ru", "Ivan");
        map.put("mail2@mail.ru", "Anna");
        map.put("mail3@mail.ru", "Inna");
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println(key + " " + value);
        }
        map.put("mail2@mail.ru", "Artem");
        map.put("mail3@mail.ru", "Lena");
        for (String key : map.keySet()) {
            String value = map.get(key);
            System.out.println(key + " " + value);
        }
    }
}
