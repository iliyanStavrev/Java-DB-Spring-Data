package com.example.springdataintro.service;

import com.example.springdataintro.model.entity.Author;
import com.example.springdataintro.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    private static final String PATH = "src/main/resources/files/authors.txt";

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public void seedAuthors() throws IOException {
        if (authorRepository.count() > 0){
            return;
        }
        Files.readAllLines(Path.of(PATH))
                .stream()
                .filter(r -> !r.isEmpty())
                .forEach(authorName ->{
                    String firstName = authorName.split("\\s+")[0];
                    String lastName = authorName.split("\\s+")[1];
                    Author author = new Author(firstName,lastName);

                    authorRepository.save(author);
                });
    }

    @Override
    public Author getRandomAuthor() {

        long randomId = ThreadLocalRandom.
                current().nextLong(1, authorRepository.count() + 1);

        return authorRepository
                .findById(randomId)
                .orElse(null);
    }

    @Override
    public List<String> findAllAuthorsWithBookSizeDesc() {
        return authorRepository
                .findAllByBooksSize()
                .stream()
                .map(a -> String.format("%s %s %d",
                        a.getFirstName(),a.getLastName(),a.getBooks().size()))
                .collect(Collectors.toList());
    }
}
