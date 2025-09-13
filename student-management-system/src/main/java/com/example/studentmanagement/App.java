package com.example.studentmanagement;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class App {
    private static final Logger logger = Logger.getLogger(App.class.getName());

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        StudentDAO dao = new StudentDAO();

        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Update Student Course");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter course: ");
                        String course = sc.nextLine();
                        dao.addStudent(new Student(name, age, course));
                        break;
                    case 2:
                        List<Student> students = dao.getAllStudents();
                        students.forEach(System.out::println);
                        break;
                    case 3:
                        System.out.print("Enter student ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new course: ");
                        String newCourse = sc.nextLine();
                        dao.updateStudentCourse(id, newCourse);
                        break;
                    case 4:
                        System.out.print("Enter student ID: ");
                        int deleteId = sc.nextInt();
                        dao.deleteStudent(deleteId);
                        break;
                    case 5:
                        System.out.println("👋 Exiting...");
                        sc.close();
                        return;
                    default:
                        System.out.println("❌ Invalid option!");
                }
            } catch (SQLException e) {
                System.out.println("❌ Error: " + e.getMessage());
            }
            try (Connection conn = DBUtil.getConnection()) {
            logger.info("✅ Connection successful! Now you can run SQL queries.");
        } catch (SQLException e) {
            logger.severe("❌ Database connection failed: " + e.getMessage());
        }
        }
    }
}
