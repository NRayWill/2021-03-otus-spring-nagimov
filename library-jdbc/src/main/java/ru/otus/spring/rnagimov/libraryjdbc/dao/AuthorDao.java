package ru.otus.spring.rnagimov.libraryjdbc.dao;

import ru.otus.spring.rnagimov.libraryjdbc.domain.Author;

import java.util.List;

public interface AuthorDao {

    long count();

    long insert(Author author);

    List<Author> getAll();

    Author getById(long id);

    void update(Author author);

    void deleteById(long id);
}
