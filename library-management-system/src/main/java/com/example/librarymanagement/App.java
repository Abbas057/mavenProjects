package com.example.librarymanagement;

import java.util.Scanner;

/**
 * CLI for Library Management System.
 */
public class App {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n📚 Library Management");
            System.out.println("1. Add Book");
            System.out.println("2. List Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    System.out.print("Enter year: ");
                    int year;
                    try {
                        year = Integer.parseInt(sc.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid year. Must be a number.");
                        continue;
                    }
                    library.addBook(title, author, year);
                }
                case 2 -> library.listBooks();
                case 3 -> {
                    System.out.print("Enter book ID to borrow: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        library.borrowBook(id);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid ID.");
                    }
                }
                case 4 -> {
                    System.out.print("Enter book ID to return: ");
                    try {
                        int id = Integer.parseInt(sc.nextLine());
                        library.returnBook(id);
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid ID.");
                    }
                }
                case 5 -> {
                    System.out.println("👋 Exiting Library Management. Goodbye!");
                    sc.close();
                    return;
                }
                default -> System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }
}
