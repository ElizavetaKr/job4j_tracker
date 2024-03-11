package ru.job4j.queue.task;

import ru.job4j.queue.task.Position;

public record Task(Position position,
                   String description,
                   int urgency) {
}
