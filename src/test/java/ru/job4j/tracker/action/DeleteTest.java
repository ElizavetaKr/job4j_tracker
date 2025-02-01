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

class DeleteTest {

    @Test
    public void whenItemWasDeleteSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Item item = tracker.add(new Item("Item"));
        Delete delete = new Delete(output);

        Input input = mock(Input.class);

        when(input.askInt(any(String.class))).thenReturn(item.getId());

        delete.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Удаление заявки ===" + ln
                        + "Заявка удалена успешно." + ln
        );
    }

    @Test
    public void whenItemWasDeleteNotSuccessfully() {
        Output output = new StubOutput();
        MemTracker tracker = new MemTracker();
        Delete delete = new Delete(output);

        Input input = mock(Input.class);

        delete.execute(input, tracker);

        String ln = System.lineSeparator();
        assertThat(output.toString()).isEqualTo(
                "=== Удаление заявки ===" + ln
                        + "Ошибка удаления заявки." + ln
        );
    }

}