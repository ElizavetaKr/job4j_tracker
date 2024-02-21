package ru.job4j.tracker;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemTest {
    @Test
    public void whenItemAscByName() {
        List<Item> items = Arrays.asList(
                new Item("nameB"),
                new Item("nameZ"),
                new Item("nameA"),
                new Item("nameG")
        );
        Collections.sort(items, new ItemAscByName());
        List<Item> expected = Arrays.asList(
                new Item("nameA"),
                new Item("nameB"),
                new Item("nameG"),
                new Item("nameZ")
        );
        assertEquals(expected, items);
    }

    @Test
    public void whenItemDescByName() {
        List<Item> items = Arrays.asList(
                new Item("nameB"),
                new Item("nameZ"),
                new Item("nameA"),
                new Item("nameG")
        );
        Collections.sort(items, new ItemDescByName());
        List<Item> expected = Arrays.asList(
                new Item("nameZ"),
                new Item("nameG"),
                new Item("nameB"),
                new Item("nameA")
        );
        assertEquals(expected, items);
    }

}