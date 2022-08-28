package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestrictionEquals(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesIsLessThan(EditionType editionType, Integer copies);

    List<Book> findAllByPriceGreaterThanOrPriceLessThan(BigDecimal price1, BigDecimal price2);

    List<Book> findAllByReleaseDateAfterOrReleaseDateBefore(LocalDate releaseDate1, LocalDate releaseDate2);

    List<Book> findAllByTitleContains(String title);

    @Query("SELECT b FROM Book b WHERE b.author.lastName LIKE :input%")
    List<Book> findAllByAuthorLastNameStartWith(@Param("input") String input);

    @Query("SELECT COUNT(b) FROM Book b WHERE LENGTH(b.title) > :length")
    Integer findAllByTitleSize(Integer length);

    @Query(value = "SELECT a.first_name,a.last_name, SUM(b.copies) AS c FROM books b " +
            "JOIN authors a on a.id = b.author_id " +
            "GROUP BY a.id " +
            "ORDER BY c DESC", nativeQuery = true)
        List<String> totalBookCopiesByAuthor();


    List<Book> findAllByTitle(String title);
}

