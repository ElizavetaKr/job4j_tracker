package ru.job4j.collection.departments;

import java.util.*;

public class Departments {

    /**
     * Метод формирует список строк и добавляет отсутствующие,
     * тем самым дополняя иерархию подразделений
     *
     * @param departments список подразделений в виде коллекции
     */
    public static List<String> fillGaps(List<String> departments) {
        Set<String> temp = new LinkedHashSet<>();
        for (String value : departments) {
            String start = "";
            for (String element : value.split("/")) {
                start += element;
                temp.add(start);
                start += "/";
            }
        }
        return new ArrayList<>(temp);
    }

    public static void sortAsc(List<String> departments) {
        Collections.sort(departments);
    }

    /**
     * Метод сотрировки по принципу:
     * сравнивает первый код подразделения элементов по убыванию,
     * если они равны, то сравнивает элементы по возрастанию
     *
     * @param departments список подразделений в виде коллекции
     */
    public static void sortDesc(List<String> departments) {
        Collections.sort(departments, new DepartmentsDescComparator());
    }
}
