package ru.job4j.tracker.input;

import org.junit.jupiter.api.Test;
import ru.job4j.tracker.output.Output;
import ru.job4j.tracker.output.StubOutput;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ValidateInputTest {

    @Test
    void whenInvalidInput() {
        Input in = new MockInput(
                new String[]{
                        "one", "1"
                }
        );
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(output, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected).isEqualTo(1);
    }

    @Test
    void whenValidInput() {
        Input in = new MockInput(
                new String[]{
                        "1"
                }
        );
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(output, in);
        int selected = input.askInt("Enter menu:");
        assertThat(selected).isEqualTo(1);
    }

    @Test
    void whenMultipleValidInput() {
        Input in = new MockInput(
                new String[]{
                        "1", "2"
                }
        );
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(output, in);
        int selectedFirst = input.askInt("Enter menu:");
        assertThat(selectedFirst).isEqualTo(1);
        int selectedSecond = input.askInt("Enter menu:");
        assertThat(selectedSecond).isEqualTo(2);
    }

    @Test
    void whenNegativeInput() {
        Input in = new MockInput(
                new String[]{
                        "-1"
                }
        );
        Output output = new StubOutput();
        ValidateInput input = new ValidateInput(output, in);
        int selectedFirst = input.askInt("Enter menu:");
        assertThat(selectedFirst).isEqualTo(-1);
    }

}