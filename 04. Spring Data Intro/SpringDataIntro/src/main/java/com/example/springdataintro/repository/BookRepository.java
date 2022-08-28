package com.example.springdataintro.repository;

import com.example.springdataintro.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate dateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate dateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle
            (String author_firstName, String author_lastName);
}
