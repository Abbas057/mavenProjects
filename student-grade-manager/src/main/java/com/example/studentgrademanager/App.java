package com.example.studentgrademanager;

import java.util.Scanner;

/**
 * Main application to manage students and grades.
 */
public class App {
    public static void main(String[] args) {
        GradeManager manager = new GradeManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n====== Student Grade Manager ======");
            System.out.println("1. Add Student");
            System.out.println("2. List Students");
            System.out.println("3. Exit");
            System.out.print("👉 Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter grade: ");
                    double grade = 0.0;
                    try {
                        grade = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Invalid grade. Please enter a number.");
                        break;
                    }

                    manager.addStudent(name, grade);
                    break;

                case "2":
                    manager.listStudents();
                    break;

                case "3":
                    System.out.println("👋 Exiting... Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
    }
}
