package com.example.tasktracker;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TaskManager {
    private List<Task> tasks;
    private int nextId;
    private static final String FILE_PATH = "tasks.json";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    public TaskManager() {
        this.tasks = new ArrayList<>();
        loadTasksFromFile(); // load tasks at startup
    }

    // Add a new task
    public void addTask(String description) {
        Task task = new Task(nextId++, description);
        tasks.add(task);
        saveTasksToFile();
        System.out.println("Task added: " + task);
    }

    // List all tasks
    public void listTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found!");
            return;
        }
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    // Update task description
    public void updateTask(int id, String newDescription) {
        Optional<Task> opt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (opt.isPresent()) {
            opt.get().setDescription(newDescription);
            saveTasksToFile();
            System.out.println("Task updated: " + opt.get());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    // Delete a task
    public void deleteTask(int id) {
        boolean removed = tasks.removeIf(t -> t.getId() == id);
        if (removed) saveTasksToFile();
        System.out.println(removed ? "Task deleted." : "Task not found with ID: " + id);
    }

    // Mark task as done
    public void markDone(int id) {
        Optional<Task> opt = tasks.stream().filter(t -> t.getId() == id).findFirst();
        if (opt.isPresent()) {
            opt.get().setStatus("done");
            saveTasksToFile();
            System.out.println("Task marked as done: " + opt.get());
        } else {
            System.out.println("Task not found with ID: " + id);
        }
    }

    // ---------------------- File I/O ----------------------

    // Save tasks to JSON file
    private void saveTasksToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {

            bw.write("[\n");
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                String line = "  {\n" +
                              "    \"id\": " + t.getId() + ",\n" +
                              "    \"description\": \"" + t.getDescription().replace("\"", "\\\"") + "\",\n" +
                              "    \"status\": \"" + t.getStatus() + "\",\n" +
                              "    \"createdAt\": \"" + t.getCreatedAt().format(formatter) + "\",\n" +
                              "    \"updatedAt\": \"" + t.getUpdatedAt().format(formatter) + "\"\n" +
                              "  }";
                if (i != tasks.size() - 1) line += ",";
                line += "\n";
                bw.write(line);
            }
            bw.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load tasks from JSON file
    private void loadTasksFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            nextId = 1;
            tasks = new ArrayList<>();
            return;
        }

        tasks = new ArrayList<>();
        int maxId = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            Task currentTask = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("\"id\"")) {
                    int id = Integer.parseInt(line.split(":")[1].trim().replace(",", ""));
                    currentTask = new Task(id, "");
                    if (id > maxId) maxId = id;
                } else if (line.startsWith("\"description\"")) {
                    String desc = line.split(":")[1].trim();
                    desc = desc.substring(1, desc.length() - 2); // remove quotes and comma
                    currentTask.setDescription(desc);
                } else if (line.startsWith("\"status\"")) {
                    String status = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                    currentTask.setStatus(status);
                } else if (line.startsWith("\"createdAt\"")) {
                    String created = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    currentTask.setCreatedAt(LocalDateTime.parse(created,formatter));
                } else if (line.startsWith("\"updatedAt\"")) {
                    String updated = line.split(":")[1].trim().replace("\"", "").replace(",", "");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    currentTask.setUpdatedAt(LocalDateTime.parse(updated, formatter));
                    tasks.add(currentTask); // add task after updatedAt
                }
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
