package com.example.tasktracker;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nCommands: add, list, update, delete, done, exit");
            System.out.print("Enter command: ");
            String command = scanner.next();

            switch (command) {
                case "add":
                    System.out.print("Enter task description: ");
                    scanner.nextLine(); // consume newline
                    String desc = scanner.nextLine();
                    manager.addTask(desc);
                    break;
                case "list":
                    manager.listTasks();
                    break;
                case "update":
                    System.out.print("Enter task ID to update: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine(); // consume newline
                    System.out.print("Enter new description: ");
                    String newDesc = scanner.nextLine();
                    manager.updateTask(updateId, newDesc);
                    break;
                case "delete":
                    System.out.print("Enter task ID to delete: ");
                    int delId = scanner.nextInt();
                    manager.deleteTask(delId);
                    break;
                case "done":
                    System.out.print("Enter task ID to mark done: ");
                    int doneId = scanner.nextInt();
                    manager.markDone(doneId);
                    break;
                case "exit":
                    running = false;
                    System.out.println("Exiting Task Tracker CLI.");
                    break;
                default:
                    System.out.println("Unknown command!");
            }
        }

        scanner.close();
    }
}
