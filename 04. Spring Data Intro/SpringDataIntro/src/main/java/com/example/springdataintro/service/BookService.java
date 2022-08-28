package com.example.springdataintro.service;

import com.example.springdataintro.model.entity.Book;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


public interface BookService {

    void seedBook() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBookBeforeYear(int year);

    List<String> findAllBooksFromAuthor(String firstName, String lastName);

}
