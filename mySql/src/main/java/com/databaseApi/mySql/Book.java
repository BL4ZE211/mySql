package com.databaseApi.mySql;

import jakarta.persistence.*;

@Entity
public class Book {

    @Id // Marks this field as the primary key (unique identifier) for the book.
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Tells the database to automatically generate this ID.
    private Long id;
    private String title;
    private String isbn;

    @ManyToOne // This is the magic! Many Books can belong to One Author.
    @JoinColumn(name = "author_id") // This tells JPA to create an 'author_id' column in the Book table
    // to store the ID of the related Author.
    private Author author; // Each book object will now hold a reference to an Author object.

    // Constructors (special methods to create Book objects)
    public Book() {
        // Default constructor - JPA needs this!
    }

    public Book(String title,  String isbn, Author author) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // Getters and Setters (methods to get and set the values of the fields)
    // Imagine these are like buttons to read or change the book's info.
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // It's also good to have a toString() method for easy printing
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}