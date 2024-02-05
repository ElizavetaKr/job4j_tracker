package ru.job4j.polymorphism;

public class Bus implements Transport {
    @Override
    public void drive() {
        System.out.println("Автобус едет");
    }

    @Override
    public void passengers(int person) {
        System.out.println("Вошли " + person + "пассажира");
    }

    @Override
    public double refuel(int fuel) {
        return fuel * 60;
    }
}
