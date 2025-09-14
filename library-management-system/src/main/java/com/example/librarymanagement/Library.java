package com.example.librarymanagement;

import java.io.*;
import java.util.*;

/**
 * Library system using a HashMap for fast book lookups.
 */
public class Library {
    private Map<Integer, Book> books; // Book ID → Book
    private int nextId;
    private static final String FILE_PATH = "library.csv";

    public Library() {
        books = new HashMap<>();
        loadFromFile();
    }

    // Add a book
    public void addBook(String title, String author, int year) {
        Book book = new Book(nextId++, title, author, year);
        books.put(book.getId(), book);
        saveToFile();
        System.out.println("✅ Book added: " + book);
    }

    // List all books
    public void listBooks() {
        if (books.isEmpty()) {
            System.out.println("⚠️ No books in library.");
            return;
        }
        for (Book book : books.values()) {
            System.out.println(book);
        }
    }

    // Borrow a book
    public void borrowBook(int id) {
        Book book = books.get(id);
        if (book == null) {
            System.out.println("⚠️ Book ID not found.");
            return;
        }
        if (book.isBorrowed()) {
            System.out.println("⚠️ Already borrowed.");
        } else {
            book.borrow();
            saveToFile();
            System.out.println("📕 You borrowed: " + book.getTitle());
        }
    }

    // Return a book
    public void returnBook(int id) {
        Book book = books.get(id);
        if (book == null) {
            System.out.println("⚠️ Book ID not found.");
            return;
        }
        if (!book.isBorrowed()) {
            System.out.println("⚠️ This book is not borrowed.");
        } else {
            book.returnBook();
            saveToFile();
            System.out.println("📗 You returned: " + book.getTitle());
        }
    }

    // Save to CSV
    private void saveToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("id,title,author,year,isBorrowed");
            bw.newLine();
            for (Book book : books.values()) {
                bw.write(book.getId() + "," +
                        book.getTitle() + "," +
                        book.getAuthor() + "," +
                        book.getYear() + "," +
                        book.isBorrowed());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load from CSV
    private void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            nextId = 1;
            return;
        }

        books = new HashMap<>();
        int maxId = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine(); // skip header
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String title = parts[1];
                String author = parts[2];
                int year = Integer.parseInt(parts[3]);
                boolean isBorrowed = Boolean.parseBoolean(parts[4]);

                Book book = new Book(id, title, author, year);
                if (isBorrowed) book.borrow();
                books.put(id, book);

                if (id > maxId) maxId = id;
            }
            nextId = maxId + 1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
