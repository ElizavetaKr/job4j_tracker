package ru.job4j.inheritance.pizza;

public class PizzaExtraCheeseExtraTomato extends PizzaExtraCheese {
    @Override
    public String name() {
        return super.name() + " + extra tomato";
    }
}
