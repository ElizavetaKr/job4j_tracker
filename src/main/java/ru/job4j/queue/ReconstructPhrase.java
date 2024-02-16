package ru.job4j.queue;

import java.util.Deque;

public class ReconstructPhrase {
    private final Deque<Character> descendingElements;

    private final Deque<Character> evenElements;

    public ReconstructPhrase(Deque<Character> descendingElements, Deque<Character> evenElements) {
        this.descendingElements = descendingElements;
        this.evenElements = evenElements;
    }

    private String getEvenElements() {
        int size = evenElements.size();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i % 2 == 0) {
                result.append(evenElements.pop());
            } else {
                evenElements.poll();
            }
        }
        return result.toString();
    }

    private String getDescendingElements() {
        int size = descendingElements.size();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(descendingElements.pollLast());
        }
        return result.toString();
    }

    public String getReconstructPhrase() {
        return getEvenElements() + getDescendingElements();
    }
}
