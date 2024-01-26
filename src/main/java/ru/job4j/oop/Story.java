package ru.job4j.oop;

public class Story {

    public static void main(String[] args) {
        Pioneer petya = new Pioneer();
        Girl girl = new Girl();
        Wolf wolf = new Wolf();

        petya.kill(wolf);
        girl.help(petya);
        wolf.eat(girl);

        Ball ball = new Ball();
        Fox fox = new Fox();
        Hare hare = new Hare();

        hare.tryEat(ball);
        wolf.tryEat(ball);
        fox.tryEat(ball);
    }
}
