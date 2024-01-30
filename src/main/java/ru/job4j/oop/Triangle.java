package ru.job4j.oop;

import static java.lang.Math.*;

public class Triangle {

    private Point first;
    private Point second;
    private Point third;

    public Triangle(Point a, Point b, Point c) {
       this.first = a;
       this.second = b;
       this.third = c;
    }

    public double semiPerimeter(double ab, double bc, double ac) {
        return (ab + bc + ac) / 2;
    }

    public boolean exist(double ab, double ac, double bc) {
        return ab + bc > ac && bc + ac > ab && ac + ab > bc;
    }

    public double area() {
        double result = -1;
        double ab = first.distance(second);
        double ac = first.distance(third);
        double bc = second.distance(third);
        if (this.exist(ab, ac, bc)) {
            double p = semiPerimeter(ab, ac, bc);
            result = sqrt(p * (p - ab) * (p - ac) * (p - bc));
        }
        return result;
    }

    public static void main(String[] args) {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(3, 3);
        Triangle triangle = new Triangle(a, b, c);
        double ab = triangle.first.distance(triangle.second);
        double ac = triangle.first.distance(triangle.third);
        double bc = triangle.second.distance(triangle.third);
        System.out.println(triangle.exist(ab, ac, bc));
        System.out.println(ab);
        System.out.println();
        System.out.println(ac);
        System.out.println();
        System.out.println(bc);
    }
}
