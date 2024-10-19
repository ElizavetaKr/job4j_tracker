package ru.job4j.tracker.action;

import ru.job4j.tracker.Store;
import ru.job4j.tracker.input.Input;
import ru.job4j.tracker.output.Output;

public class Exit implements UserAction {
    private final Output output;

    public Exit(Output output) {
        this.output = output;
    }

    @Override
    public String name() {
        return "Завершить программу";
    }

    @Override
    public boolean execute(Input input, Store tracker) {
        output.println("=== Завершение программы ===");
        return false;
    }
}
