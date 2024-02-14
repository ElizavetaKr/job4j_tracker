package ru.job4j.tracker;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.action.*;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.input.MockInput;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class StartUITest {
    @Test
    void whenCreateItem() {
        Output output = new StubOutput();
        Input input = new MockInput(
                new String[]{"0", "Item name", "1"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = Arrays.asList(
                new Create(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        assertThat(tracker.findAll().get(0).getName()).isEqualTo("Item name");
    }

    @Test
    void whenReplaceAction() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Replaced item"));
        Input input = new MockInput(
                new String[]{"0", String.valueOf(item.getId()), "New item name", "1"}
        );
        List<UserAction> actions = Arrays.asList(
                new Replace(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        assertThat(tracker.findById(item.getId()).getName()).isEqualTo("New item name");
    }

    @Test
    void whenDeleteAction() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("Deleted item"));
        Input input = new MockInput(
                new String[]{"0", String.valueOf(item.getId()), "1"}
        );
        List<UserAction> actions = Arrays.asList(
                new Delete(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        assertThat(tracker.findById(item.getId())).isNull();
    }

    @Test
    void whenExit() {
        Output output = new StubOutput();
        Input input = new MockInput(
                new String[]{"0"}
        );
        Tracker tracker = new Tracker();
        List<UserAction> actions = Arrays.asList(
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        assertThat(output.toString()).isEqualTo(
                "Меню:" + System.lineSeparator()
                        + "0. Завершить программу" + System.lineSeparator()
                        + "=== Завершение программы ===" + System.lineSeparator()
        );
    }

    @Test
    void whenFindAllActionTestOutputIsSuccessfully() {
        Output output = new StubOutput();
        Input input = new MockInput(
                new String[]{"0", "1"}
        );
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("New item"));
        List<UserAction> actions = Arrays.asList(
                new FindAll(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "Меню:" + ln
                        + "0. Показать все заявки" + ln
                        + "1. Завершить программу" + ln
                        + "=== Вывод всех заявок ===" + ln
                        + item + ln
                        + "Меню:" + ln
                        + "0. Показать все заявки" + ln
                        + "1. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenReplaceItemTestOutputIsSuccessfully() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("New item"));
        String replaceName = "New Test Name";
        Input input = new MockInput(new String[]{
                "0", String.valueOf(item.getId()), replaceName, "1"
        });
        List<UserAction> actions = Arrays.asList(
                new Replace(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "Меню:" + ln
                        + "0. Изменить заявку" + ln
                        + "1. Завершить программу" + ln
                        + "=== Редактирование заявки ===" + ln
                        + "Заявка изменена успешно." + ln
                        + "Меню:" + ln
                        + "0. Изменить заявку" + ln
                        + "1. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenFindByNameActionTestOutputIsSuccessfully() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("New item"));
        Input input = new MockInput(new String[]{
                "0", item.getName(), "1"
        });
        List<UserAction> actions = Arrays.asList(
                new FindByName(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "Меню:" + ln
                        + "0. Показать заявки по имени" + ln
                        + "1. Завершить программу" + ln
                        + "=== Вывод заявок по имени ===" + ln
                        + item + ln
                        + "Меню:" + ln
                        + "0. Показать заявки по имени" + ln
                        + "1. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenFindByIdActionTestOutputIsSuccessfully() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Item item = tracker.add(new Item("New item"));
        Input input = new MockInput(new String[]{
                "0", String.valueOf(item.getId()), "1"
        });
        List<UserAction> actions = Arrays.asList(
                new FindById(output),
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "Меню:" + ln
                        + "0. Показать заявку по id" + ln
                        + "1. Завершить программу" + ln
                        + "=== Вывод заявки по id ===" + ln
                        + item + ln
                        + "Меню:" + ln
                        + "0. Показать заявку по id" + ln
                        + "1. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln);
    }

    @Test
    void whenInvalidExit() {
        Output output = new StubOutput();
        Tracker tracker = new Tracker();
        Input input = new MockInput(new String[]{
                "7", "0"
        });
        List<UserAction> actions = Arrays.asList(
                new Exit(output)
        );
        new StartUI(output).init(input, tracker, actions);
        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "Меню:" + ln
                        + "0. Завершить программу" + ln
                        + "Неверный ввод, вы можете выбрать: 0 .. 0" + ln
                        + "Меню:" + ln
                        + "0. Завершить программу" + ln
                        + "=== Завершение программы ===" + ln);

    }
}