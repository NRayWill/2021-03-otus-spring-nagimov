package ru.otus.spring.rnagimov.libraryorm.repository;

import ru.otus.spring.rnagimov.libraryorm.model.Book;

import java.util.List;

public interface BookRepository {

    long count();

    Book insert(Book book);

    List<Book> getAll();

    Book getById(long id);

    void update(Book book);

    int deleteById(long id);
}
