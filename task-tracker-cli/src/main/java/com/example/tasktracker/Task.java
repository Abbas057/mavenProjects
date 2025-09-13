package com.example.tasktracker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    private int id;
    private String description;
    private String status; // todo, in-progress, done
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

     // Formatter for nice output
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    // Constructor
    public Task(int id, String description) {
        this.id = id;
        this.description = description;
        this.status = "todo";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { 
        this.description = description; 
        this.updatedAt = LocalDateTime.now();
    }
    public String getStatus() { return status; }
    public void setStatus(String status) { 
        this.status = status; 
        this.updatedAt = LocalDateTime.now();
    }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    public void setCreatedAt(LocalDateTime localDateTime) {
        throw new UnsupportedOperationException("Unimplemented method 'setCreatedAt'");
    }

    public void setUpdatedAt(LocalDateTime localDateTime) {
        throw new UnsupportedOperationException("Unimplemented method 'setUpdatedAt'");
    }
    @Override
    public String toString() {
        return "ID: " + id + ", Description: " + description + ", Status: " + status +
               ", CreatedAt: " + createdAt.format(formatter) + ", UpdatedAt: " + updatedAt.format(formatter);
    }

    
}

