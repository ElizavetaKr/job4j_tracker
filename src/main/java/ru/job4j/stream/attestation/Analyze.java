package ru.job4j.stream.attestation;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Analyze {
    /**
     * Метод вычисляет общий средний балл.
     *
     * @param stream поток учеников
     */
    public static double averageScore(Stream<Pupil> stream) {
        return stream
                .flatMap(pupil -> pupil.subjects().stream())
                .mapToInt(Subject::score)
                .average()
                .orElse(0D);
    }

    /**
     * Метод вычисляет средний балл по каждому ученику.
     *
     * @param stream поток учеников
     * @return возвращает список из объекта Tuple (имя ученика и средний балл)
     */
    public static List<Tuple> averageScoreByPupil(Stream<Pupil> stream) {
        return stream
                .map(pupil -> new Tuple(pupil.name(),
                        pupil.subjects()
                                .stream()
                                .mapToInt(Subject::score)
                                .average()
                                .orElse(0D)
                ))
                .collect(Collectors.toList());
    }

    /**
     * Метод вычисляет средний балл по каждому предмету.
     *
     * @param stream поток учеников
     * @return возвращает список из объекта Tuple (название предмета и средний балл)
     */
    public static List<Tuple> averageScoreBySubject(Stream<Pupil> stream) {
        return stream
                .flatMap(pupil -> pupil.subjects().stream())
                .collect(
                        Collectors.groupingBy(
                                Subject::name,
                                Collectors.averagingDouble(Subject::score)
                        )
                )
                .entrySet()
                .stream()
                .map(entry -> new Tuple(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    /**
     * Метод возвращает лучшего ученика.
     *
     * @param stream поток учеников
     * @return возвращает объект Tuple (имя ученика и суммарный балл)
     */
    public static Tuple bestStudent(Stream<Pupil> stream) {
        return stream
                .map(pupil -> new Tuple(pupil.name(),
                        pupil.subjects()
                                .stream()
                                .mapToInt(Subject::score)
                                .sum()))
                .max(Comparator.comparing(Tuple::score))
                .orElse(null);
    }

    /**
     * Метод возвращает предмет с наибольшим баллом для всех студентов.
     *
     * @param stream поток учеников
     * @return возвращает объект Tuple (имя предмета, сумма баллов каждого ученика по этому предмету)
     */
    public static Tuple bestSubject(Stream<Pupil> stream) {
        return stream
                .flatMap(pupil -> pupil.subjects().stream())
                .collect(
                        Collectors.groupingBy(
                                Subject::name,
                                LinkedHashMap::new,
                                Collectors.summingDouble(Subject::score)
                        )
                )
                .entrySet()
                .stream()
                .map(entry -> new Tuple(entry.getKey(), entry.getValue()))
                .max(Comparator.comparing(Tuple::score))
                .orElse(null);
    }
}