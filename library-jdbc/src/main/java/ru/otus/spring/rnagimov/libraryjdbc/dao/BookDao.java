package ru.otus.spring.rnagimov.libraryjdbc.dao;

import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {

    long count();

    Optional<Long> insert(Book book);

    List<Book> getAll();

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);
}
