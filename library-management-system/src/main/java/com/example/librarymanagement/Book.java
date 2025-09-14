package com.example.librarymanagement;

/**
 * Represents a book in the library.
 */
public class Book {
    private int id;
    private String title;
    private String author;
    private int year;
    private boolean isBorrowed;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isBorrowed = false;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getYear() { return year; }
    public boolean isBorrowed() { return isBorrowed; }

    // Borrow/Return
    public void borrow() { this.isBorrowed = true; }
    public void returnBook() { this.isBorrowed = false; }

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " (" + year + ") " +
                (isBorrowed ? "[Borrowed]" : "[Available]");
    }
}
