package com.databaseApi.mySql;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String country;

    // We'll add books here later
    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER) // "mappedBy" points to the 'author' field in the Book entity
    private List<Book> books;

    public Author() {
    }

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    // Getters and Setters for id, name, country
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    // public List<Book> getBooks() { return books; }
    // public void setBooks(List<Book> books) { this.books = books; }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
