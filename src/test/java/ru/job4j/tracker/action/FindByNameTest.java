package ru.job4j.tracker.action;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.Item;
import ru.job4j.tracker.MemTracker;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FindByNameTest {
    @Test
    public void whenFindByNameSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = tracker.add(new Item("Item"));
        String name = "Item";
        FindByName findByNameAction = new FindByName(output);

        Input input = mock(Input.class);

        when(input.askStr(any(String.class))).thenReturn(name);

        findByNameAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявок по имени ===" + ln
                        + item + ln
        );
    }

    @Test
    public void whenFindByNameNotSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        String name = "Item";
        FindByName findByNameAction = new FindByName(output);

        Input input = mock(Input.class);

        when(input.askStr(any(String.class))).thenReturn(name);

        findByNameAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявок по имени ===" + ln
                        + "Заявки с именем: " + name + " не найдены." + ln
        );
    }
}