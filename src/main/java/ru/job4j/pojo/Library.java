package ru.job4j.pojo;

public class Library {
    public static void main(String[] args) {
        Book fantastic = new Book("1984", 320);
        Book classic = new Book("Idiot", 640);
        Book horror = new Book("It", 1248);
        Book code = new Book("Clean code", 520);
        Book[] books = new Book[4];
        books[0] = fantastic;
        books[1] = classic;
        books[2] = horror;
        books[3] = code;
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].getName() + " - " + books[i].getPages());
        }
        System.out.println("Swapping books with index 0 and 3");
        books[0] = code;
        books[3] = fantastic;
        for (int i = 0; i < books.length; i++) {
            System.out.println(books[i].getName() + " - " + books[i].getPages());
        }
        System.out.println("Book with name: \"Clean code\"");
        for (int i = 0; i < books.length; i++) {
            String desiredBook = "Clean code";

            if (desiredBook.equals(books[i].getName())) {
                System.out.println(books[i].getName() + " - " + books[i].getPages());
            }
        }
    }
}
