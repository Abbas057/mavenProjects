package com.example.studentgrademanager;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages students: add, list, and save/load from CSV file.
 */
public class GradeManager {
    private List<Student> students;
    private int nextId;
    private static final String FILE_PATH = "grades.csv";

    public GradeManager() {
        students = new ArrayList<>();
        loadFromFile();
    }

    // Add a new student
    public void addStudent(String name, double grade) {
        Student student = new Student(nextId++, name, grade);
        students.add(student);
        saveToFile();
        System.out.println("✅ Student added: " + student);
    }

    // List all students
    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println("⚠️ No students found.");
            return;
        }
        for (Student s : students) {
            System.out.println(s);
        }
    }

    // Save students to CSV
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            // Write header
            bw.write("id,name,grade,createdAt");
            bw.newLine();

            // Write each student
            for (Student s : students) {
                String line = s.getId() + "," +
                        s.getName() + "," +
                        s.getGrade() + "," +
                        s.getCreatedAt();  // already a formatted string
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load students from CSV
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            nextId = 1;
            return;
        }

        students = new ArrayList<>();
        int maxId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 4) continue; // skip invalid rows

                int id = Integer.parseInt(parts[0]);
                String name = parts[1];
                double grade = Double.parseDouble(parts[2]);
                String createdAt = parts[3];  // keep formatted string

                Student student = new Student(id, name, grade, createdAt);
                students.add(student);

                if (id > maxId)
                    maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
