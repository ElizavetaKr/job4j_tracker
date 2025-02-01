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

class FindByIdTest {
    @Test
    public void whenFindByIdSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = tracker.add(new Item("Item"));
        FindById findByIdAction = new FindById(output);

        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(item.getId());

        findByIdAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявки по id ===" + ln
                        + item + ln
        );
    }

    @Test
    public void whenFindByIdNotSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        FindById findByIdAction = new FindById(output);

        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(1);

        findByIdAction.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Вывод заявки по id ===" + ln
                        + "Заявка с введенным id: " + 1 + " не найдена." + ln
        );
    }
}