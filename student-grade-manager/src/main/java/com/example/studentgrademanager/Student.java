package com.example.studentgrademanager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Student with id, name, grade, and createdAt timestamp.
 */
public class Student {
    private int id;
    private String name;
    private double grade;
    private String createdAt;  // store as formatted string

    // Formatter for formatting/parsing
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Constructor for new students (auto-sets current time)
    public Student(int id, String name, double grade) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.createdAt = LocalDateTime.now().format(formatter);
    }

    // Constructor for loading from file (createdAt already formatted string)
    public Student(int id, String name, double grade, String createdAt) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.createdAt = createdAt;
    }

    // Getters
    public int getId() { return id; }
    public String getName() { return name; }
    public double getGrade() { return grade; }
    public String getCreatedAt() { return createdAt; }

    // Setters
    public void setName(String name) { this.name = name; }
    public void setGrade(double grade) { this.grade = grade; }

    @Override
    public String toString() {
        return id + " | " + name + " | " + grade + " | CreatedAt: " + createdAt;
    }
}
