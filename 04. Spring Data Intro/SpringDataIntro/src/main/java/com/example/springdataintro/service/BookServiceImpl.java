package com.example.springdataintro.service;

import com.example.springdataintro.model.entity.*;
import com.example.springdataintro.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String PATH = "src/main/resources/files/books.txt";


    private final AuthorService authorService;
    private final BookRepository bookRepository;
    private final CategoryService categoryService;

    public BookServiceImpl(AuthorService authorService, BookRepository bookRepository, CategoryService categoryService) {
        this.authorService = authorService;
        this.bookRepository = bookRepository;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBook() throws IOException {

        if (bookRepository.count() > 0) {
            return;
        }

        Files.readAllLines(Path.of(PATH))
                .stream()
                .filter(row -> !row.isEmpty())
                .forEach(info -> {
                    String[] bookInfo = info.split("\\s+");
                    EditionType editionType =
                            EditionType.values()[Integer.parseInt(bookInfo[0])];
                    LocalDate releaseDate = LocalDate.parse(bookInfo[1],
                            DateTimeFormatter.ofPattern("d/M/yyyy"));
                    Integer copies = Integer.parseInt(bookInfo[2]);
                    BigDecimal price = new BigDecimal(bookInfo[3]);
                    AgeRestriction ageRestriction =
                            AgeRestriction.values()[Integer.parseInt(bookInfo[4])];
                    String title = Arrays.stream(bookInfo)
                            .skip(5)
                            .collect(Collectors.joining(" "));
                    Author author = authorService.getRandomAuthor();
                    Set<Category> categories = categoryService.getRandomCategories();

                    Book book = new Book(editionType, releaseDate, copies,
                            price, ageRestriction, title, author, categories);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {

        return bookRepository.
                findAllByReleaseDateAfter(LocalDate.of(2000, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBookBeforeYear(int year) {

        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(1990, 1, 1))
                .stream()
                .map(book ->
                        String.format("%s %s", book.getAuthor().getFirstName(),
                                book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksFromAuthor(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle
                        (firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(), book.getReleaseDate(), book.getCopies()))
                .collect(Collectors.toList());
    }

}
