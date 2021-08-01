package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.model.Author;

import java.util.List;

public interface AuthorRepository {

    long count();

    long insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    void update(Author author);

    int deleteById(long id);
}
