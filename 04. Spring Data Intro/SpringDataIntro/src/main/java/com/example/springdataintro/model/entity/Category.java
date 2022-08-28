package com.example.springdataintro.model.entity;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity(name = "categories")
public class Category extends BaseEntity{
    private String name;
    private Set<Book>books;

    public Category() {
    }

    public Category(String catName) {
        this.name = catName;
    }


    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   @ManyToMany(mappedBy = "categories")
    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(name, category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
