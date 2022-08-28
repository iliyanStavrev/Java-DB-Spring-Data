package com.example.springintro;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {
    Scanner scanner = new Scanner(System.in);

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
         seedData();
        // printAllBooksAfterYear(2000);
        // printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        // printAllAuthorsAndNumberOfTheirBooks();
        // printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");
         task_1_printAllBooksTitle();
        // task_2_printGoldenEditionBooks();
        // task_3_printTitlesAndPrice();
        // task_4_notReleasedBooks();
        // task_5_booksReleasedBeforeDate();
        // task_6_authorsSearch();
        // task_7_booksSearch();
        // task_8_bookTitlesSearch();
        // task_9_countBooks();
        // task_10_totalBookCopies();
        // task_11_reducedBook();


    }

    private void task_11_reducedBook() {
        String title = scanner.nextLine();
        this.bookService
                .getBookByTitle(title)
                .forEach(System.out::println);
    }

    private void task_10_totalBookCopies() {
        this.bookService
                .totalBookCopiesByAuthor()
                .forEach(System.out::println);
    }

    private void task_9_countBooks() {
        Integer length = Integer.parseInt(scanner.nextLine());
        System.out.println(this.bookService
                .findAllByTitleSize(length));
    }

    private void task_8_bookTitlesSearch() {
        String input = scanner.nextLine();
        this.bookService
                .findAllByAuthorLastNameStartWith(input)
                .forEach(System.out::println);
    }

    private void task_7_booksSearch() {
        String input = scanner.nextLine();
        this.bookService
                .findAllByTitleContains(input)
                .forEach(System.out::println);
    }

    private void task_6_authorsSearch() {
        String input = scanner.nextLine();
        this.authorService
                .findAllByFirstNameEndingWith(input)
                .forEach(System.out::println);
    }

    private void task_5_booksReleasedBeforeDate() {
        String[] input = scanner.nextLine().split("-");
        int day = Integer.parseInt(input[0]);
        int month = Integer.parseInt(input[1]);
        int year = Integer.parseInt(input[2]);
        LocalDate releasedDate = LocalDate.of(year,month,day);

        this.bookService
                .findAllByReleaseDateBefore(releasedDate)
                .forEach(System.out::println);
    }

    private void task_4_notReleasedBooks() {
        int year = Integer.parseInt(scanner.nextLine());
       LocalDate date1 = LocalDate.of(year, 12, 31);
       LocalDate date2 = LocalDate.of(year, 1, 1);

        this.bookService
               .findAllByReleaseDateNotLike(date1,date2)
               .forEach(System.out::println);

    }

    private void task_3_printTitlesAndPrice() {
        BigDecimal price1 = BigDecimal.valueOf(40);
        BigDecimal price2 = BigDecimal.valueOf(5);
        this.bookService
                .findAllByPriceGreaterThanOrPriceLessThan(price1,price2)
                .forEach(System.out::println);
    }

    private void task_2_printGoldenEditionBooks() {
        EditionType editionType = EditionType.GOLD;
        Integer copies = 5000;
        this.bookService
                .findAllByEditionTypeAndCopiesIsLessThan(editionType, copies)
                .forEach(System.out::println);
    }

    private void task_1_printAllBooksTitle() {
        AgeRestriction restriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());
        this.bookService
                .findAllByAgeRestrictionEquals(restriction)
                .forEach(System.out::println);
    }

//    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
//        bookService
//                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsAndNumberOfTheirBooks() {
//        authorService
//                .getAllAuthorsOrderByCountOfTheirBooks()
//                .forEach(System.out::println);
//    }
//
//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }
//
//    private void printAllBooksAfterYear(int year) {
//        bookService
//                .findAllBooksAfterYear(year)
//                .stream()
//                .map(Book::getTitle)
//                .forEach(System.out::println);
//    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
