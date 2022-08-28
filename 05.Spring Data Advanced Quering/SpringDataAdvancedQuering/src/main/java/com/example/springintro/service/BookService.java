package com.example.springintro.service;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    List<String> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies);

    List<String> findAllByPriceGreaterThanOrPriceLessThan(BigDecimal price1, BigDecimal price2);

    List<String> findAllByReleaseDateNotLike(LocalDate date1, LocalDate date2);

    List<String> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<String> findAllByTitleContains(String title);

    List<String> findAllByAuthorLastNameStartWith(String input);

    Integer findAllByTitleSize(Integer length);

    List<String> totalBookCopiesByAuthor();

    List<String> getBookByTitle(String title);
}
