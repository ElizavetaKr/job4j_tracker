package ru.job4j.oop;

public class Error {

    boolean active;
    int status;
    String message;


    public Error() {
    }

    public Error(boolean active, int status, String message) {
        this.active = active;
        this.status = status;
        this.message = message;
    }

    public void printInfo() {
        System.out.println("Value active: " + active);
        System.out.println("Value status: " + status);
        System.out.println("Value message: " + message);
    }

    public static void main(String[] args) {
        Error error1 = new Error();
        Error error2 = new Error(true, 1, "Error");
        Error error3 = new Error(false, 2, "No error");

        error1.printInfo();
        error2.printInfo();
        error3.printInfo();
    }
}
