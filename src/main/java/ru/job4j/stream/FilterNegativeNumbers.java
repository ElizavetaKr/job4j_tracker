package ru.job4j.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class FilterNegativeNumbers {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, -2, 0, 5, 55, -32, 110);
        List<Integer> positive = numbers.stream().filter(num -> num > 0)
                .collect(Collectors.toList());
        positive.forEach(System.out::println);
    }
}
