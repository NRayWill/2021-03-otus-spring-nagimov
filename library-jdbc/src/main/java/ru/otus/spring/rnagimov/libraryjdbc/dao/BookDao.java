package ru.otus.spring.rnagimov.libraryjdbc.dao;

import ru.otus.spring.rnagimov.libraryjdbc.domain.Book;

import java.util.List;

public interface BookDao {

    long count();

    long insert(Book book);

    List<Book> getAll();

    Book getById(long id);

    void update(Book book);

    void deleteById(long id);
}
