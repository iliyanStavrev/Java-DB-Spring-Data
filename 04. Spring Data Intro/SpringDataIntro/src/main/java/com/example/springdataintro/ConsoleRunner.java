package com.example.springdataintro;

import com.example.springdataintro.model.entity.Book;
import com.example.springdataintro.service.AuthorService;
import com.example.springdataintro.service.BookService;
import com.example.springdataintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public ConsoleRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedDatabase();
        printAllBooksAfter2000(2000);
        printAllNamesOfAuthorsWithBookBeforeYear(1990);
        printAllAuthorsBookSize();
        printAllBooksOfAuthor("George", "Powell");


    }

    private void printAllBooksOfAuthor(String fName, String lName) {
        bookService
                .findAllBooksFromAuthor(fName,lName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsBookSize() {
        authorService
                .findAllAuthorsWithBookSizeDesc()
                .forEach(System.out::println);
    }

    private void printAllNamesOfAuthorsWithBookBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBookBeforeYear(year)
                .forEach(System.out::println);

    }

    private void printAllBooksAfter2000(int year) {
        bookService.findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedDatabase() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBook();
    }
}
