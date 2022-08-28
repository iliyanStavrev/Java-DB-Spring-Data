package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
        return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction) {
        return this.bookRepository
                .findAllByAgeRestrictionEquals(ageRestriction)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies) {
        return this.bookRepository
                .findAllByEditionTypeAndCopiesIsLessThan(editionType, copies)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByPriceGreaterThanOrPriceLessThan(BigDecimal price1, BigDecimal price2) {
        return this.bookRepository
                .findAllByPriceGreaterThanOrPriceLessThan(price1, price2)
                .stream()
                .map(b -> String.format("%s - $%.2f",
                        b.getTitle(), b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByReleaseDateNotLike(LocalDate date1, LocalDate date2) {
        return this.bookRepository
                .findAllByReleaseDateAfterOrReleaseDateBefore(date1, date2)
                .stream()
                .map(b -> String.format("%s", b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByReleaseDateBefore(LocalDate releaseDateBefore) {
        return this.bookRepository
                .findAllByReleaseDateBefore(releaseDateBefore)
                .stream()
                .map(b -> String.format("%s %s %.2f",
                        b.getTitle(), b.getEditionType(),
                        b.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByTitleContains(String title) {
        return this.bookRepository
                .findAllByTitleContains(title)
                .stream()
                .map(b -> String.format("%s",
                        b.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllByAuthorLastNameStartWith(String input) {
        return this.bookRepository
                .findAllByAuthorLastNameStartWith(input)
                .stream()
                .map(b -> String.format("%s (%s %s)",
                        b.getTitle(),b.getAuthor().getFirstName(),
                        b.getAuthor().getLastName()))
                .collect(Collectors.toList());
    }

    @Override
    public Integer findAllByTitleSize(Integer length) {
        return this.bookRepository
                .findAllByTitleSize(length);
    }

    @Override
    public List<String> totalBookCopiesByAuthor() {
        return this.bookRepository
                .totalBookCopiesByAuthor()
                .stream()
                .map(o -> String.format("%s %s - %s",
                        o.split(",")[0],
                        o.split(",")[1],
                        o.split(",")[2]))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getBookByTitle(String title) {
        return this.bookRepository
                .findAllByTitle(title)
                .stream()
                .map(b -> String.format("%s %s %s %.2f",
                        b.getTitle(),b.getEditionType(),
                        b.getAgeRestriction(),b.getPrice()))
                .collect(Collectors.toList());
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
